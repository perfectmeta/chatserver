package com.perfectword.semantic_kernel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class OpenAI {
    public static OpenAiService makeOpenAiService() {
        OkHttpClient client = makeHttpClient();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://111.207.225.93:4000/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

    private static OkHttpClient makeHttpClient() {
        return OpenAiService.defaultClient("sk-", Duration.of(15, ChronoUnit.SECONDS))
                .newBuilder()
                .build();
    }
}
