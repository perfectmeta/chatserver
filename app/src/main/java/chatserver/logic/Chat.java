package chatserver.logic;

import chatserver.dao.BotClass;
import chatserver.dao.Message;
import chatserver.dao.User;
import chatserver.gen.ChatRequest;
import chatserver.gen.ChatResponseStream;
import chatserver.gen.MsgType;
import chatserver.service.BotClassService;
import chatserver.service.RoomService;
import chatserver.third.tts.XFYtts;
import chatserver.util.Digest;
import com.google.common.base.Strings;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

@Component
public class Chat {
    private static final Logger logger = Logger.getLogger(Chat.class.getName());

    private final RoomService roomService;
    private final BotClassService botClassService;
    private final String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : ".";


    @Autowired
    public Chat(RoomService roomService, BotClassService botClassService) {
        this.roomService = roomService;
        this.botClassService = botClassService;
    }

    public void run(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        // check room id first
        User user = AuthTokenInterceptor.USER.get();
        var room = roomService.findRoomById(request.getRoomId());
        if (room == null || room.getUserId() != user.getUserId()) {
            logger.warning("invalid room id: " + request.getRoomId());
            responseObserver.onCompleted();
            return;
        }
        BotClass botClass = botClassService.findBotClassById(room.getAiUserId());
        logger.info("AI Prompt" + botClass.getPrompt());
        var prompt = botClass.getPrompt();
        OpenAiService service = makeOpenAiService();
        List<Message> messageHistory = roomService.getMessageHistory(request.getRoomId());

        var newUserMsg = roomService.addMessage(parseDbMessage(request));
        //chatserver.gen.Message requestMessage = Msg.fromDb(newUserMsg);

        var messageSeq = request.getSeq();
        chatserver.gen.Message.Builder rr = chatserver.gen.Message.newBuilder()
                .setMessageId(newUserMsg.getMessageId())
                .setSeq(messageSeq);
        ChatResponseStream firstResponse = ChatResponseStream.newBuilder()
                .setRequestMessage(rr)
                .build();
        responseObserver.onNext(firstResponse);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt);
        messages.add(systemMessage);

        for (Message msg : messageHistory) {
            String role = ChatMessageRole.USER.value();
            if (msg.getAuthorUserType() != Msg.UT_HUMAN) {
                role = ChatMessageRole.ASSISTANT.value();
            }
            var content = msg.getText();
            if (Strings.isNullOrEmpty(content)) {
                content = "";
                logger.warning("content is null " + msg.getMessageId());
            }
            ChatMessage cm = new ChatMessage(role, content);
            messages.add(cm);
        }
        var requestText = request.getText();
        if (Strings.isNullOrEmpty(requestText)) {
            requestText = "?";
        }
        final ChatMessage ask = new ChatMessage(ChatMessageRole.USER.value(), requestText);
        messages.add(ask);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .build();

        boolean[] hasError = {false};
        StringBuilder gptReturn = new StringBuilder();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    responseObserver.onError(throwable);
                    hasError[0] = true;
                })
                .blockingForEach(chatCompletionChunk -> {
                    if (chatCompletionChunk.getChoices().size() > 0) {
                        ChatCompletionChoice choice = chatCompletionChunk.getChoices().get(0);
                        String content = choice.getMessage().getContent();
                        if (content == null) {
                            content = "";
                        }
                        gptReturn.append(content);
                        ChatResponseStream text = ChatResponseStream.newBuilder()
                                .setText(content)
                                .build();
                        responseObserver.onNext(text);
                    }
                });

        String allContent = gptReturn.toString();
        String url = "";
        if (!Strings.isNullOrEmpty(allContent)) {
            url = saveTTS(allContent);
        }

        if (hasError[0]) {
            logger.warning("chat gpt returned error");
        }
        Message gptMsg = new Message();
        gptMsg.setRoomId(request.getRoomId());
        gptMsg.setAuthorUserType(Msg.UT_AI_TEACHER);
        gptMsg.setAuthorShowName("teacher");
        gptMsg.setCreatedTime(System.currentTimeMillis());
        gptMsg.setMsgType(MsgType.TEXT_VALUE);
        gptMsg.setText(gptReturn.toString());
        gptMsg.setAudioUrl(url);
        gptMsg = roomService.addMessage(gptMsg);

        var responseMessage = chatserver.gen.Message.newBuilder()
                .setMessageId(gptMsg.getMessageId())
                .setText(allContent)
                .setAudioUrl(url)
                .setSeq(messageSeq);

        var lastResponse = ChatResponseStream.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(lastResponse);
        responseObserver.onCompleted();

        // service.shutdownExecutor();
    }

    private Message parseDbMessage(ChatRequest request) {
        Message newUserMsg = new Message();
        newUserMsg.setRoomId(request.getRoomId());
        newUserMsg.setAuthorUserType(Msg.UT_HUMAN);
        newUserMsg.setAuthorUserId(AuthTokenInterceptor.USER.get().getUserId());
        newUserMsg.setAuthorShowName(AuthTokenInterceptor.USER.get().getNickName());
        newUserMsg.setCreatedTime(System.currentTimeMillis());
        newUserMsg.setMsgType(MsgType.TEXT_VALUE);
        newUserMsg.setText(request.getText());
        return newUserMsg;
    }


    private String saveTTS(String allContent) {
        ByteBuffer tempFile = ByteBuffer.allocate(1024*1024);
        String fileName;
        try(var inputStream = XFYtts.makeSession(allContent)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len=inputStream.read(buffer)) != -1) {
                tempFile.put(buffer, 0, len);
            }
            tempFile.flip();
            var size = tempFile.remaining();
            var content = tempFile.array();
            fileName = size+ "_" + Digest.calculateMD5(tempFile) + ".pcm";
            Path file;
            try {
                //这一步可能文件已经存在了，就不需要再写入文件，直接返回文件名即可
                file = Files.createFile(Path.of(resourcePath, fileName));
            } catch (IOException e) {
                return fileName;
            }
            try (var fout = new FileOutputStream(file.toFile())) {
                fout.write(content, 0, size);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }
}
