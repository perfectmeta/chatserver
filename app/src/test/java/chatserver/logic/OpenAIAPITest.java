package chatserver.logic;

import chatserver.security.Secrets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class OpenAIAPITest {
    private static final Logger logger = Logger.getLogger(OpenAIAPITest.class.getName());
    @Test
    void connectTest() {
        logger.info("OpenAiKey " + Secrets.OPENAI_KEY);
        OpenAiService service = new OpenAiService(Secrets.OPENAI_KEY);
        CompletionRequest completionRequest = CompletionRequest.builder()
            .prompt("Somebody once told me the world is gonna roll me")
            .model("ada")
            .echo(true)
            .build();
        logger.info("after here, it's the result");
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }

    @Test
    void chatGPTTest() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 1080));
        OkHttpClient client =
                OpenAiService.defaultClient(Secrets.OPENAI_KEY, Duration.of(5, ChronoUnit.SECONDS))
                        .newBuilder()
                        .proxy(proxy)
                        .build();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);
        ChatMessage cm = new ChatMessage();
        cm.setContent("Hello GPT");
        cm.setRole("user");
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(cm))
                .stream(true)
                .build();
        logger.info("After here are results");
        var stream = service.streamChatCompletion(request);
        stream.blockingForEach(chunk->
                System.out.print(chunk.getChoices().get(0).getMessage().getContent()));
    }
}
