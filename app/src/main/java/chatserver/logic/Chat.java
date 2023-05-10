package chatserver.logic;

import chatserver.dao.Message;
import chatserver.gen.ChatRequest;
import chatserver.gen.ChatResponseStream;
import chatserver.gen.MsgType;
import chatserver.service.RoomService;
import chatserver.third.tts.XFYtts;
import chatserver.util.Digest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.grpc.stub.StreamObserver;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chatserver.security.KeyManager;
import retrofit2.Retrofit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Chat {
    private static final Logger logger = Logger.getLogger(Chat.class.getName());

    @Autowired
    private RoomService roomService;

    public void run(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 1080));
        OkHttpClient client =
                OpenAiService.defaultClient(KeyManager.OPENAI_KEY, Duration.of(5, ChronoUnit.SECONDS))
                        .newBuilder()
                        .proxy(proxy)
                        .build();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);

        List<Message> messageHistory = roomService.getMessageHistory(request.getRoomId());

        Message newUserMsg = new Message();
        newUserMsg.setRoomId(request.getRoomId());
        newUserMsg.setAuthorUserType(Msg.UT_HUMAN);
        newUserMsg.setAuthorUserId(AuthTokenInterceptor.USER.get().getUserId());
        newUserMsg.setAuthorShowName(AuthTokenInterceptor.USER.get().getNickName());
        newUserMsg.setCreatedTime(System.currentTimeMillis());
        newUserMsg.setMsgType(MsgType.TEXT_VALUE);
        newUserMsg.setText(request.getText());
        newUserMsg = roomService.addMessage(newUserMsg);

        chatserver.gen.Message requestMessage = Msg.fromDb(newUserMsg);

        ChatResponseStream firstResponse = ChatResponseStream.newBuilder().setRequestMessage(requestMessage).build();
        responseObserver.onNext(firstResponse);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), KeyManager.TEACHER_PROMPT);
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
                        ChatResponseStream text = ChatResponseStream.newBuilder().setText(content).build();
                        responseObserver.onNext(text);
                    }
                });

        String allContent = gptReturn.toString();
        String url = "";
        if (Strings.isNullOrEmpty(allContent)) {
            url = saveTTS(allContent);
        }

        if (!hasError[0]) {
            responseObserver.onCompleted();
        }

        Message gptMsg = new Message();
        gptMsg.setRoomId(request.getRoomId());
        gptMsg.setAuthorUserType(Msg.UT_AI_TEACHER);
        gptMsg.setAuthorShowName("teacher");
        gptMsg.setCreatedTime(System.currentTimeMillis());
        gptMsg.setMsgType(MsgType.TEXT_VALUE);
        gptMsg.setText(gptReturn.toString());
        gptMsg.setAudioUrl(url);
        roomService.addMessage(gptMsg);

        // service.shutdownExecutor();
    }

    private String saveTTS(String allContent) {
        ByteBuffer tempFile = ByteBuffer.allocate(1024*1024);
        try(var inputStream = XFYtts.makeSession(allContent)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len=inputStream.read(buffer)) != -1) {
                tempFile.put(buffer, 0, len);
            }
            tempFile.flip();
            var size = tempFile.remaining();
            var content = tempFile.array();
            var fileName = size+ "_" + Digest.calculateMD5(tempFile) + ".pcm";
            var file = Files.createFile(Path.of(fileName));
            try (var fout = new FileOutputStream(file.toFile())) {
                fout.write(content);
            }
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
