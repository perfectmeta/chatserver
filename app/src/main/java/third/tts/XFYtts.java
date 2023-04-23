package third.tts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import security.KeyManager;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import security.authentication.xfy.AuthURLEncoder;
import third.tts.entity.LTTSResponse;
import third.tts.entity.XFYLTTSRequestContent;

public class XFYtts {
    public static final Logger logger = Logger.getLogger(XFYtts.class.getName());
    public static final String lttsHostCreate = "https://api-dx.xf-yun.com/v1/private/dts_create";
    public static final String lttsHostQuery = "https://api-dx.xf-yun.com/v1/private/dts_query";
    public static final String ttsHost = "https://tts-api.xfyun.cn/v2/tts";

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
            .build();


    private static String makeRequestContent(String content) {
        ObjectMapper mapper = new ObjectMapper();
        var builder = new XFYLTTSRequestContent.Builder();
        builder.header.app_id = KeyManager.XFY_APPID;
        builder.parameter.dts.vcn = "x4_pengfei";
        builder.parameter.dts.language = "zh";
        builder.parameter.dts.speed = 50;
        builder.parameter.dts.volume = 50;
        builder.parameter.dts.pitch = 50;
        builder.parameter.dts.rhy = 1;
        builder.parameter.dts.audio.encoding = "lame";
        builder.parameter.dts.audio.sample_rate = 16000;
        builder.parameter.dts.pybuf.encoding = "utf8";
        builder.parameter.dts.pybuf.compress = "raw";
        builder.parameter.dts.pybuf.format = "plain";
        builder.payload.text.encoding = "utf8";
        builder.payload.text.compress = "raw";
        builder.payload.text.format = "plain";
        byte[] base64Content;
        base64Content = content.getBytes(StandardCharsets.UTF_8);
        builder.payload.text.text = Base64.getEncoder().encodeToString(base64Content);

        try {
            var requestBody = mapper.writeValueAsString(builder.build());
            logger.info(requestBody);
            return mapper.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 流式API，走的是websocket协议，使用讯飞流式tts api接口
    public static XFYTTSSession makeSession(String content) {
        String url;
        try {
            url = AuthURLEncoder.encodeXFYAuthorUrl(ttsHost, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "GET");
            url = url.replace("https:", "wss:");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info(url);
        var session = new XFYTTSSession(URI.create(url));
        session.connect();
        return session;
    }

    // 请求响应式TTS，走的是http请求，采用一次提交多次查询的方式来获取回应
    public static LTTSResponse request(String content) {
        var url = AuthURLEncoder.encodeXFYAuthorUrl(lttsHostCreate, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "POST");
        String requestContent = makeRequestContent(content);
        RequestBody requestBody = RequestBody.create(requestContent, MediaType.parse("application/json;charset=utf-8"));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try (Response response = client.newCall(request).execute()){
            if (response.code() != 101) {
                logger.warning("status code " + response.code() + ": " +
                        Objects.requireNonNull(response.body()).string());
                return null;
            }
            var body = Objects.requireNonNull(response.body()).string();
            logger.info(body);
            return new LTTSResponse(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
