package chatserver.third.tts;

import chatserver.security.Secrets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Pwrdtts {
    public static final Logger logger = Logger.getLogger(Pwrdtts.class.getName());

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
            .build();


    public record TTSResponse(int code, String message, String text, String type, String result) {
    }

    public static byte[] tts(String txt, String speakerName) {
        if (Strings.isNullOrEmpty(speakerName)) {
            speakerName = Secrets.PWRDTTS_DEFAULT_SPEAKER;
        }
        String url = String.format("http://%s/%s/tts?speaker=%s&emotion=normal&return_type=mp3&text=%s",
                Secrets.PWRDTTS_HOST, speakerName, speakerName, txt);
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                logger.warning("status code " + response.code() + ": " +
                        Objects.requireNonNull(response.body()).string());
                throw new IllegalStateException("Http Connection Error, code: " + response.code());
            }

            String body = Objects.requireNonNull(response.body()).string();
            ObjectMapper mapper = new ObjectMapper();
            var res = mapper.readValue(body, TTSResponse.class);
            if (res.code() == 20000) {
                return Base64.getDecoder().decode(res.result);
            } else {
                throw new IllegalStateException("response resolved error code " + res.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
