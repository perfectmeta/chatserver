package com.perfectword.semantic_kernel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class OpenAI {
    static String dispatcher = "";
    static String openAiKey = "";
    static {
        try (var fi = new FileInputStream("../../dispatcher.txt")) {
            dispatcher = new String(fi.readAllBytes(), StandardCharsets.UTF_8) ;
        } catch (IOException e) {
            dispatcher = System.getenv("dispatcher");
            if (dispatcher == null || dispatcher.isEmpty()) {
                throw new RuntimeException(e);
            }
        }

        try (var fr = new FileInputStream("../../openaikey.txt")) {
            openAiKey = new String(fr.readAllBytes(), StandardCharsets.UTF_8); ;
        } catch (IOException e) {
            openAiKey = System.getenv("openaikey");
            throw new RuntimeException(e);
        }
    }
    public static OpenAiService makeOpenAiService() {
        OkHttpClient client = makeHttpClient();
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(dispatcher)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

    private static OkHttpClient makeHttpClient() {
        return OpenAiService.defaultClient(openAiKey, Duration.of(15, ChronoUnit.SECONDS))
                .newBuilder()
                .build();
    }
}
