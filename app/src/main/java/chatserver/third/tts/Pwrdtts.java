package chatserver.third.tts;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Server;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;

public class Pwrdtts {


    public static final Logger logger = Logger.getLogger(Pwrdtts.class.getName());

    public static String ServerPort = "10.6.12.163:5002";

    /*
    {
     "code": 20000,
     "message": "Success",
     "text": "xxx",
     "type":"wav",
     "result":"Base64encode(audio_file)"
     "dutaion":{'ce4':2,'shi4':3}
     "emoji_status":20000
     "emoji":[[1.1,1,2],
              [2.2,2.3]]
    }
    */

    public record TTSResponse(int code, String message, String text, String type, String result) {

    }

    public static byte[] tts(String txt) {
        String url = String.format("http://%s/tts?speaker=yaohua&emotion=normal&text=%s", ServerPort, txt);
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
                byte[] result = Base64.getDecoder().decode(res.result);
                return result;
            } else {
                logger.info("code = " + res.code());
                return null;
            }

        } catch (IOException e) {
            return null;
        }


    }
}
