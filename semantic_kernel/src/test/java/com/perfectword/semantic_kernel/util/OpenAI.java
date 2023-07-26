package com.perfectword.semantic_kernel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class OpenAI {
    public static OpenAiService makeOpenAiService() {
        String openAIKey;
        String openAIBaseURL;

        String secretFile = System.getenv("chatserver.secret");
        if (secretFile == null) {
            secretFile = "../config/secret.properties";
        }

        var properties = new Properties();
        try (var fileInputStream = new FileInputStream(secretFile)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        openAIBaseURL = properties.getProperty("openai.base-url");
        openAIKey = properties.getProperty("openai.key");

        OkHttpClient client = OpenAiService.defaultClient(openAIKey, Duration.of(15, ChronoUnit.SECONDS))
                .newBuilder()
                .build();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(openAIBaseURL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        @SuppressWarnings("deprecation") OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

}
