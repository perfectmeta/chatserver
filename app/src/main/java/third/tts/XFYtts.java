package third.tts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import okhttp3.*;
import security.KeyManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

import security.authentication.xfy.AuthURLEncoder;
import third.tts.entity.LTTSResponse;

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
        var builder = new third.tts.entity.create.req.Request.Builder();
        builder.header.app_id = KeyManager.XFY_APPID;
        builder.parameter.dts.vcn = "x4_yeting";
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

    private static String makeQueryContent(String taskId) {
        var builder = new third.tts.entity.query.req.Request.Builder();
        builder.header.app_id = KeyManager.XFY_APPID;
        builder.header.task_id = taskId;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 流式API，走的是websocket协议，使用讯飞流式tts api接口
    public static InputStream makeSession(String content) {
        String url;
        try {
            url = AuthURLEncoder.encodeXFYAuthorUrl(ttsHost, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "GET");
            url = url.replace("https:", "wss:");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var session = new XFYTTSSession(URI.create(url), content);
        session.connect();
        return session.getInputStream();
    }

    // 请求响应式TTS，走的是http请求，采用一次提交多次查询的方式来获取回应
    public static LTTSResponse request(String content) {
        var task_id = createLTTSTask(content);
        if (Strings.isNullOrEmpty(task_id)) {
            logger.warning("Create LTTS task failed, content " + content);
            return new LTTSResponse(1);
        }
        var res = new LTTSResponse(0);
        res.rawContent = waitForLTTSTask(task_id);
        return res;
    }

    public static void requestAsync(String content, Consumer<LTTSResponse> callback) {
        Thread.startVirtualThread(()->{
            var audioRes = request(content);
            callback.accept(audioRes);
        });
    }

    private static String createLTTSTask(String content) {
        var url = AuthURLEncoder.encodeXFYAuthorUrl(lttsHostCreate, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "POST");
        String requestContent = makeRequestContent(content);
        RequestBody requestBody = RequestBody.create(requestContent, MediaType.parse("application/json;charset=utf-8"));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try (Response response = client.newCall(request).execute()){
            if (response.code() != 200) {
                logger.warning("status code " + response.code() + ": " +
                        Objects.requireNonNull(response.body()).string());
                return null;
            }
            var body = Objects.requireNonNull(response.body()).string();
            ObjectMapper mapper = new ObjectMapper();
            var res = mapper.readValue(body, third.tts.entity.create.res.Response.class);
            if (res.header().code() == 0) {
                logger.info(res.header().task_id());
                return res.header().task_id();
            } else {
                logger.info(String.valueOf(res.header().code()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] waitForLTTSTask(String taskId) {
        var buffer = ByteBuffer.allocate(100000);
        var finished = false;
        do {
            var url = AuthURLEncoder.encodeXFYAuthorUrl(lttsHostQuery, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "POST");
            var queryContent = makeQueryContent(taskId);
            var requestBody = RequestBody.create(queryContent, MediaType.parse("application/json;charset=utf-8"));
            var request = new Request.Builder().url(url).post(requestBody).build();
            try (var response = client.newCall(request).execute()){
                if (response.code() != 200) {
                    logger.warning("Task query failed! code: " + response.code()
                            + " body: " + Objects.requireNonNull(response.body()).string());
                    return new byte[0];
                }
                var body = Objects.requireNonNull(response.body()).string();
                var om = new ObjectMapper();
                var content = om.readValue(body, third.tts.entity.query.res.Response.class);
                if (!Objects.isNull(content.payload())) {
                    buffer.put(Base64.getDecoder().decode(content.payload().audio().audio()));
                    if (content.header().task_status().equals("5")) {
                        finished = true;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!finished) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } while (!finished);
        return buffer.array();
    }
}
