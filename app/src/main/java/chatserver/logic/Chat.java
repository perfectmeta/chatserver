package chatserver.logic;

import chatserver.config.Config;
import chatserver.config.Prompt;
import chatserver.config.robot.Robot;
import chatserver.entity.*;
import chatserver.gen.ChatRequest;
import chatserver.gen.ChatResponseStream;
import chatserver.logic.internal.SummaryMemory;
import chatserver.logic.voice.PwrdSpeaker;
import chatserver.logic.voice.VoiceTransfer;
import chatserver.service.ContactService;
import chatserver.service.RoomService;
import chatserver.service.UserService;
import chatserver.util.TokenLimitor;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

import static chatserver.third.openai.OpenAi.makeOpenAiService;


@Component
public class Chat {
    private static final int MEMORY_INTERVAL = 2;
    private static final Logger logger = Logger.getLogger(Chat.class.getName());
    private final RoomService roomService;
    private final UserService userService;
    private final ContactService contactService;
    private final SummaryMemory summaryMemory;
    private final OpenAiService service = makeOpenAiService();
    static final String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : ".";

    @Autowired
    public Chat(RoomService roomService,
                UserService userService,
                ContactService contactService,
                SummaryMemory summaryMemory) {
        this.roomService = roomService;
        this.userService = userService;
        this.contactService = contactService;
        this.summaryMemory = summaryMemory;
    }

    public void run(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        var room = roomService.findRoomById(request.getRoomId());
        if (room == null || room.getUserId() != user.getUserId()) {
            logger.warning("invalid room id: " + request.getRoomId());
            responseObserver.onError(new IllegalStateException("Invalid room id: " + request.getRoomId()));
            return;
        }
        logger.info("AI UserId is " + room.getAiUserId());
        User aiUser = userService.findByUserId(room.getAiUserId());
        if (aiUser == null) {
            logger.warning("Can't find robot By Id: " + room.getAiUserId());
            responseObserver.onError(new IllegalStateException("Can't find robot By Id: " + room.getAiUserId()));
            return;
        }

        Robot robot = Config.getInstance().getRobotByName(aiUser.getBotId());
        if (robot == null) {
            logger.warning("Can't find robot config : " + aiUser.getBotId());
            responseObserver.onError(new IllegalStateException("Can't find robot config : " + aiUser.getBotId()));
            return;
        }

        List<Message> messageHistory = roomService.getMessageHistory(request.getRoomId());
        Collections.reverse(messageHistory);

        var newUserMsg = roomService.addMessage(parseDbMessage(request));
        var messageSeq = request.getSeq();

        VoiceTransfer voiceTransfer = new VoiceTransfer((data, finished) -> {
            ChatResponseStream audioResponse = ChatResponseStream.newBuilder()
                    .setAudio(ByteString.copyFrom(data))
                    .build();
            responseObserver.onNext(audioResponse);
        }, resourcePath, new PwrdSpeaker(robot.speaker()));

        chatserver.gen.Message.Builder rr = chatserver.gen.Message.newBuilder()
                .setMessageId(newUserMsg.getMessageId())
                .setSeq(messageSeq);
        ChatResponseStream firstResponse = ChatResponseStream.newBuilder()
                .setRequestMessage(rr)
                .build();
        responseObserver.onNext(firstResponse);

        final List<ChatMessage> messages = new ArrayList<>();
        Memory memory = contactService.getNewestMemory(room.getAiUserId(), user.getUserId());
        var tempVariables = new HashMap<String, String>();
        tempVariables.put("botname", robot.configName());
        tempVariables.putAll(AuthTokenInterceptor.VARIABLES.get());
        var prompt = robot.prompt().getMessage(tempVariables);
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
                logger.warning("content is null " + msg.getMessageId());
                continue;
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

        var tokenCnt = TokenLimitor.limit(messages, 4097, 10);
        logger.info("Chat use token " + tokenCnt);

        // debugPrintPrompt(messages);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(robot.model())
                .messages(messages)
                .build();

        boolean[] hasError = {false};
        StringBuilder gptReturn = new StringBuilder();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    responseObserver.onError(throwable);
                    hasError[0] = true;
                    logger.warning(throwable.getMessage());
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
            return;
        }

        Message gptMsg = saveDBMessage(request.getRoomId(),
                room.getAiUserId(),
                robot.configName(),
                gptReturn.toString());

        var responseMessage = chatserver.gen.Message.newBuilder()
                .setMessageId(gptMsg.getMessageId())
                .setText(allContent)
                .setSeq(messageSeq);

        var lastResponse = ChatResponseStream.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(lastResponse);
        final Message dbGptMsg = gptMsg;
        voiceTransfer.finish((fileName) -> {
            dbGptMsg.setAudioUrl(fileName);
            roomService.updateMessage(dbGptMsg);

            ChatResponseStream text = ChatResponseStream.newBuilder()
                    .setResponseMessage(chatserver.gen.Message.newBuilder().setAudioUrl(fileName).build())
                    .build();
            responseObserver.onNext(text);
            responseObserver.onCompleted();
        });

    }

    private void summaryMemory(User user,
                               Memory memory,
                               String robotName,
                               List<Message> messageHistory,
                               Message message,
                               List<ChatMessage> messages,
                               Room room) {
        var role2Name = new HashMap<String, String>();
        role2Name.put(ChatMessageRole.USER.value(), user.getNickName());
        role2Name.put(ChatMessageRole.ASSISTANT.value(), robotName);
        if (memory == null) {
            if (messageHistory.size() >= MEMORY_INTERVAL) {
                String newSummary = summaryMemory.run(parseMessageList(messages, role2Name), "",
                        messageHistory.get(messageHistory.size() - 1).getMessageId());
                Memory newMemory = new Memory();
                newMemory.setMemo(newSummary);
                newMemory.setUserId(room.getAiUserId());
                newMemory.setCreatedTime(System.currentTimeMillis());
                newMemory.setOtherUserId(user.getUserId());
                contactService.addMemory(newMemory);
            }
        } else {
            var lastMemoryId = memory.getCreateMessageId();
            var skippedMessagesCnt = 0;
            for (int i = messageHistory.size() - 1; i >= 0; i--) {
                if (messageHistory.get(i).getMessageId() > lastMemoryId) {
                    skippedMessagesCnt += 1;
                } else {
                    break;
                }
            }
            if (skippedMessagesCnt >= MEMORY_INTERVAL) {
                String newSummary = summaryMemory.run(parseMessageList(
                                messages.subList(Math.max(0, messages.size() - skippedMessagesCnt), messages.size()),
                                role2Name), memory.getMemo(),
                        messageHistory.get(messageHistory.size() - 1).getMessageId());

                Memory newMemory = new Memory();
                newMemory.setMemo(newSummary);
                newMemory.setUserId(room.getAiUserId());
                newMemory.setCreatedTime(System.currentTimeMillis());
                newMemory.setOtherUserId(user.getUserId());
                contactService.addMemory(newMemory);
            }
        }
    }

    private void preparePromptMessage(List<ChatMessage> messages, List<Prompt.PromptMessage> promptMessages) {
        for (Prompt.PromptMessage(Prompt.PromptRole role, String content) : promptMessages) {
            messages.add(new ChatMessage(parseRole(role).value(), content));
        }
    }

    private static ChatMessageRole parseRole(Prompt.PromptRole promptRole) {
        return switch (promptRole) {
            case Assistant -> ChatMessageRole.ASSISTANT;
            case User -> ChatMessageRole.USER;
            case System -> ChatMessageRole.SYSTEM;
        };
    }

    @SuppressWarnings("unused")
    private static void debugPrintPrompt(List<ChatMessage> messages) {
        logger.info("Total prompt items is {%d}".formatted(messages.size()));
        messages.forEach(m -> logger.info("[" + m.getRole() + "]:" + m.getContent()));
    }

    private Message saveDBMessage(long roomId, long userId, String categoryName, String text) {
        Message gptMsg = new Message();
        gptMsg.setRoomId(roomId);
        gptMsg.setAuthorUserType(EUserType.BOT);
        gptMsg.setAuthorShowName(categoryName);
        gptMsg.setAuthorUserId(userId);
        gptMsg.setCreatedTime(System.currentTimeMillis());
        gptMsg.setMsgType(EMsgType.TEXT_WITH_AUDIO);
        gptMsg.setText(text);
        return roomService.addMessage(gptMsg);
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

    private List<String> parseMessageList(List<ChatMessage> messageList, Map<String, String> role2Name) {
        var result = new ArrayList<String>();
        for (ChatMessage message : messageList) {
            if (message.getRole().equals(ChatMessageRole.USER.value())) {
                result.add(role2Name.get(ChatMessageRole.USER.value()) + ":" + message.getContent());
            } else if (message.getRole().equals(ChatMessageRole.ASSISTANT.value())) {
                result.add(role2Name.get(ChatMessageRole.ASSISTANT.value()) + ":" + message.getContent());
            }
        }
        return result;
    }
}
