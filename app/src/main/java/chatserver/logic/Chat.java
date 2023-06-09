package chatserver.logic;

import chatserver.entity.*;
import chatserver.gen.ChatRequest;
import chatserver.gen.ChatResponseStream;
import chatserver.service.ContactService;
import chatserver.service.RoomService;
import chatserver.service.UserCategoryService;
import chatserver.third.tts.XFYtts;
import chatserver.util.Digest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

class VoiceTransfer {
    private static final Logger logger = Logger.getLogger(VoiceTransfer.class.getName());
    enum Status {
        READY,
        START,
        FINISHED
    }
    private static final String[] _spliters = {",", "。", ".", "，", "?", "？", "!", "！"};
    private static final Set<String> spliters = new HashSet<>(Arrays.asList(_spliters));
    private final BiConsumer<byte[], Boolean> callBack;
    private final Queue<String> queue;
    private final ByteBuffer byteBuffer;
    private StringBuilder tempFragment;
    private Status status;

    public VoiceTransfer(BiConsumer<byte[], Boolean> callBack) {
        this.callBack = callBack;
        this.queue = new LinkedBlockingDeque<>();
        this.status = Status.READY;
        this.tempFragment = new StringBuilder();
        this.byteBuffer = ByteBuffer.allocate(1024*1024);
    }

    public void update(String newMessage) {
        int offset = 0;
        for (int i = 0; i < newMessage.length(); i++) {
            if (spliters.contains(newMessage.substring(i, i+1))) {
                tempFragment.append(newMessage, offset, i+1);
                offset = i+1;
                var fragment = tempFragment.toString();
                if (!Strings.isNullOrEmpty(fragment)) {
                    addTask(fragment);
                    logger.info("Add Task: " + fragment);
                }
                tempFragment = new StringBuilder();
            }
        }

        tempFragment.append(newMessage, offset, newMessage.length());

        if (status == Status.READY) {
            status = Status.START;
            Thread.startVirtualThread(() -> {
                while (status != Status.FINISHED || !queue.isEmpty()) {
                    var content = queue.poll();
                    if (Strings.isNullOrEmpty(content)) {
                        continue;
                    }
                    try (var audioStream = XFYtts.makeSession(content)) {
                        byte[] audio = audioStream.readAllBytes();
                        byteBuffer.put(audio);
                        boolean finished = status == Status.FINISHED && queue.isEmpty();
                        callBack.accept(audio, finished);
                        if (finished) {
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                byteBuffer.flip();
                logger.info("saving TTS");
                saveTTS(byteBuffer.array(), 0, byteBuffer.limit());
            });
        }
    }

    private void addTask(String content) {
        queue.add(content);
    }

    public void finish() {
        this.status = Status.FINISHED;
    }

    private String saveTTS(byte[] allContent, int offset, int length) {
        var fileName = length + "_" + Digest.calculateMD5(allContent, offset, length) + ".mp3";
        Path file;
        try {
            //这一步可能文件已经存在了，就不需要再写入文件，直接返回文件名即可
            file = Files.createFile(Path.of(Chat.resourcePath, fileName));
            logger.info("Save mp3 file " + file);
        } catch (IOException e) {
            logger.warning("conflict file " + fileName);
            return fileName;
        }
        try (var fout = new FileOutputStream(file.toFile())) {
            fout.write(allContent, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}

@Component
public class Chat {
    private static final Logger logger = Logger.getLogger(Chat.class.getName());

    private final RoomService roomService;
    private final UserCategoryService userCategoryService;

    private final ContactService contactService;
    static final String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : ".";

    @Autowired
    public Chat(RoomService roomService, UserCategoryService userCategoryService, ContactService contactService) {
        this.roomService = roomService;
        this.userCategoryService = userCategoryService;
        this.contactService = contactService;
    }

    public void run(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        // check room id first
        User user = AuthTokenInterceptor.USER.get();
        var room = roomService.findRoomById(request.getRoomId());
        if (room == null || room.getUserId() != user.getUserId()) {
            logger.warning("invalid room id: " + request.getRoomId());
            responseObserver.onError(new IllegalStateException("Invalid room id: " + request.getRoomId()));
            // responseObserver.onCompleted();
            return;
        }
        logger.info("AI UserId is " + room.getAiUserId());
        UserCategory userCategory = userCategoryService.findUserCategoryByUserId(room.getAiUserId());
        var prompt = userCategory.getPrompt();
        OpenAiService service = makeOpenAiService();
        List<Message> messageHistory = roomService.getMessageHistory(request.getRoomId());

        var newUserMsg = roomService.addMessage(parseDbMessage(request));

        var messageSeq = request.getSeq();

        VoiceTransfer voiceTransfer = new VoiceTransfer((data, finished)->{
            chatserver.gen.Message.Builder r = chatserver.gen.Message.newBuilder()
                    .setSeq(messageSeq);
            ChatResponseStream firstResponse = ChatResponseStream.newBuilder()
                    .setRequestMessage(r)
                    .build();
            responseObserver.onNext(firstResponse);
            if (finished) {
                responseObserver.onCompleted();
            }

            // todo update url to database
        });

        chatserver.gen.Message.Builder rr = chatserver.gen.Message.newBuilder()
                .setMessageId(newUserMsg.getMessageId())
                .setSeq(messageSeq);
        ChatResponseStream firstResponse = ChatResponseStream.newBuilder()
                .setRequestMessage(rr)
                .build();
        responseObserver.onNext(firstResponse);

        final List<ChatMessage> messages = new ArrayList<>();
        Memory memory = contactService.getNewestMemory(user.getUserId(), room.getAiUserId());
        preparePromptMessage(messages, prompt);
        if (memory != null) {
            String memoryPrompt = "以下是你和将要和你对话的用户的一些对话摘要:" + memory.getMemo();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), memoryPrompt));
        }
        // debugPrintPrompt(messages);
        for (Message msg : messageHistory) {
            String role = ChatMessageRole.USER.value();
            if (msg.getAuthorUserType() != EUserType.HUMAN) {
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
                        voiceTransfer.update(content);
                        responseObserver.onNext(text);
                    }
                });

        String allContent = gptReturn.toString();

        if (hasError[0]) {
            logger.warning("chat gpt returned error");
        }
        Message gptMsg = new Message();
        gptMsg.setRoomId(request.getRoomId());
        gptMsg.setAuthorUserType(EUserType.BOT);
        gptMsg.setAuthorShowName(userCategory.getUserCategoryName());
        gptMsg.setCreatedTime(System.currentTimeMillis());
        gptMsg.setMsgType(EMsgType.TEXT_WITH_AUDIO);
        gptMsg.setText(gptReturn.toString());
        gptMsg = roomService.addMessage(gptMsg);

        var responseMessage = chatserver.gen.Message.newBuilder()
                .setMessageId(gptMsg.getMessageId())
                .setText(allContent)
                .setSeq(messageSeq);

        var lastResponse = ChatResponseStream.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(lastResponse);
        voiceTransfer.finish();
        // responseObserver.onCompleted();
    }

    @Data
    public static class PromptItem {
        enum RoleType {
            SYSTEM("system"),
            ASSISTANT("assistant"),
            USER("user");

            private final String strValue;

            RoleType(String value) {
                this.strValue = value;
            }

            public String getStrValue() {
                return strValue;
            }
        }

        private String role;
        private String content;
    }

    private void preparePromptMessage(List<ChatMessage> messages, String prompt) {
        var prompts = prompt.split("\n");
        var mapper = new ObjectMapper();
        for (var p : prompts) {
            if (Strings.isNullOrEmpty(p)) {
                continue;
            }

            try {
                var pJson = mapper.readValue(p, PromptItem.class);
                if (PromptItem.RoleType.SYSTEM.getStrValue().equals(pJson.role)) {
                    messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), pJson.content));
                } else if (PromptItem.RoleType.ASSISTANT.getStrValue().equals(pJson.role)) {
                    messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), pJson.content));
                } else if (PromptItem.RoleType.USER.getStrValue().equals(pJson.role)) {
                    messages.add(new ChatMessage(ChatMessageRole.USER.value(), pJson.content));
                } else {
                    throw new IllegalArgumentException("Unknown role %s ".formatted(pJson.role));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unused")
    private static void debugPrintPrompt(List<ChatMessage> messages) {
        logger.info("Total prompt items is {%d}".formatted(messages.size()));
        messages.forEach(m -> logger.info("[" + m.getRole() + "]:" + m.getContent()));
    }

    private Message parseDbMessage(ChatRequest request) {
        Message newUserMsg = new Message();
        newUserMsg.setRoomId(request.getRoomId());
        newUserMsg.setAuthorUserType(EUserType.HUMAN);
        newUserMsg.setAuthorUserId(AuthTokenInterceptor.USER.get().getUserId());
        newUserMsg.setAuthorShowName(AuthTokenInterceptor.USER.get().getNickName());
        newUserMsg.setCreatedTime(System.currentTimeMillis());
        newUserMsg.setMsgType(EMsgType.TEXT);
        newUserMsg.setText(request.getText());
        return newUserMsg;
    }

}
