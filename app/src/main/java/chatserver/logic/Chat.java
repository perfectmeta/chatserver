package chatserver.logic;

import chatserver.dao.Message;
import chatserver.gen.ChatRequest;
import chatserver.gen.ChatResponseStream;
import chatserver.gen.MsgType;
import chatserver.service.RoomService;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chatserver.security.KeyManager;

import java.util.ArrayList;
import java.util.List;

@Component
public class Chat {

    @Autowired
    private RoomService roomService;

    public void run(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        OpenAiService service = new OpenAiService(KeyManager.OPENAI_KEY);

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
            ChatMessage cm = new ChatMessage(role, msg.getText());
            messages.add(cm);
        }

        final ChatMessage ask = new ChatMessage(ChatMessageRole.USER.value(), request.getText());
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
                        if (content == null) { // 不知道为什么会这样
                            content = "";
                        }
                        gptReturn.append(content);
                        ChatResponseStream text = ChatResponseStream.newBuilder().setText(content).build();
                        responseObserver.onNext(text);

                        // TODO: tts
                    }
                });

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
        roomService.addMessage(gptMsg);

        service.shutdownExecutor();
    }
}
