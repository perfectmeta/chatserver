package chatserver.third.openai;

import chatserver.security.KeyManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class OpenAi {

    public static OpenAiService makeOpenAiService() {
        //Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 1080));
        OkHttpClient client = makeHttpClient();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://111.207.225.93:4000/")
                //.baseUrl("https://api.openai.com/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

    private static OkHttpClient makeHttpClient() {
        // todo, set a socket factory to make socket option tcp_no_delay enabled
        return OpenAiService.defaultClient(KeyManager.OPENAI_KEY, Duration.of(15, ChronoUnit.SECONDS))
                .newBuilder()
                .build();
    }
}
