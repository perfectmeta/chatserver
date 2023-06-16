package chatserver.third.tts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;

public class Pwrdtts {


    public static final Logger logger = Logger.getLogger(Pwrdtts.class.getName());

    public static String ttsHost = "ip:port";
    public static String speaker = "zhy";

    static {
        String envHost = System.getenv("pwrdtts_host");
        if (!Strings.isNullOrEmpty(envHost)) {
            ttsHost = envHost;
        }
        String envSpeaker = System.getenv("pwrdtts_speaker");
        if (!Strings.isNullOrEmpty(envSpeaker)) {
            speaker = envSpeaker;
        }
    }

    public record TTSResponse(int code, String message, String text, String type, String result) { }

    public static byte[] tts(String txt) {
        String url = String.format("http://%s/%s/tts?speaker=%s&emotion=normal&return_type=mp3&text=%s",
                ttsHost, speaker, speaker, txt);
        Request request = new Request.Builder().url(url).build();

        try (Response response = XFYtts.client.newCall(request).execute()) {
            if (response.code() != 200) {
                logger.warning("status code " + response.code() + ": " +
                        Objects.requireNonNull(response.body()).string());
                return null;
            }

            String body = Objects.requireNonNull(response.body()).string();
            ObjectMapper mapper = new ObjectMapper();
            var res = mapper.readValue(body, TTSResponse.class);
            if (res.code() == 20000) {
                return Base64.getDecoder().decode(res.result);
            } else {
                logger.info("code = " + res.code());
                return null;
            }

        } catch (IOException e) {
            return null;
        }


    }
}
