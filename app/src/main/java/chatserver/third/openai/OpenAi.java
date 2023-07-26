package chatserver.third.openai;

import chatserver.security.Secrets;
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
        // todo, set a socket factory to make socket option tcp_no_delay enabled
        OkHttpClient client = OpenAiService.defaultClient(Secrets.OPENAI_KEY, Duration.of(15, ChronoUnit.SECONDS))
                .newBuilder()
                .build();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Secrets.OPENAI_BASEURL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        @SuppressWarnings("deprecation") OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

}
