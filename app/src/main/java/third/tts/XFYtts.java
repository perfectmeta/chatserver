package third.tts;

import okhttp3.*;
import security.KeyManager;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import security.authentication.xfy.AuthURLEncoder;
import third.tts.entity.TTSResponse;

public class XFYtts {
    public static final Logger logger = Logger.getLogger(XFYtts.class.getName());
    public static final String ttsHost = "https://api-dx.xf-yun.com/v1/private/dts_create";

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
            .build();

    public static TTSResponse request(String content) {
        var url = AuthURLEncoder.encodeAuthorUrl(ttsHost, KeyManager.TTS_API_KEY, KeyManager.TTS_API_SECRET);
        RequestBody requestBody = RequestBody.create(content, MediaType.get("application/x-www-form-urlencoded"));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try (Response response = client.newCall(request).execute()){
            if (response.code() != 101) {
                logger.warning("status code " + response.code() + ": " + response.body().string());
                return null;
            }
            var body = response.body().byteString().toString();
            return new TTSResponse(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
