package chatserver.logic;

import chatserver.security.KeyManager;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatTest {
    private OpenAiService service;

    @BeforeAll
    void init() {
        service = new OpenAiService(KeyManager.OPENAI_KEY);
    }

    @AfterAll
    void destroy() {
        service.shutdownExecutor();
    }


    @Test
    void chatTest() {
        chat("hello");
        chat("what's up");

        chat("what do you think of elo mask?");

    }

    @Test
    void chatTestParallel() throws InterruptedException {
        Thread t = Thread.startVirtualThread(this::chatTest2);
        Thread t2 = Thread.startVirtualThread(this::chatTest3);

        t.join();
        t2.join();
    }


    void chatTest2() {
        chat("do you feel good");
        chat("are you ok");
        chat("do you think agi will come in five years?");
    }


    void chatTest3() {
        chat("do you feel good");
        chat("are you ok");
        chat("do you think agi will come in five years?");
    }

    String chat(String msg) {
        System.out.println("-----" + msg);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), KeyManager.TEACHER_PROMPT);
        messages.add(systemMessage);

        final ChatMessage ask = new ChatMessage(ChatMessageRole.USER.value(), msg);
        messages.add(ask);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .build();

        StringBuilder gptReturn = new StringBuilder();
        StringBuilder gptReturnWithSep = new StringBuilder();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(throwable -> {
                    System.out.println(throwable.toString());
                    throwable.printStackTrace();
                })
                .blockingForEach(chatCompletionChunk -> {
                    if (chatCompletionChunk.getChoices().size() > 0) {
                        ChatCompletionChoice choice = chatCompletionChunk.getChoices().get(0);
                        String content = choice.getMessage().getContent();
                        if (content == null) { // 不知道为什么会这样
                            content = "";
                        }
                        gptReturn.append(content);
                        gptReturnWithSep.append(content).append("---");
//                        System.out.println("recv: " + content);
                    }
                });
        System.out.println("-----" + gptReturn);
        System.out.println("-----" + gptReturnWithSep);
        return gptReturn.toString();
    }

}