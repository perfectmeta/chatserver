package chatserver.third.asr;

import chatserver.third.asr.entity.stream.req.Request;
import chatserver.third.asr.entity.stream.res.Response;
import chatserver.third.asr.entity.stream.res.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.handshake.ServerHandshake;
import chatserver.security.KeyManager;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class XFYASRSession extends org.java_websocket.client.WebSocketClient implements ASRSession {
    public static final Logger logger = Logger.getLogger(XFYASRSession.class.getName());
    public static final int AUDIO_BUFFER_SIZE = 1280;

    enum SessionStatus {
        CREATE,
        RUNNING,
        WAITING_FOR_REMOTE,
        FINISHED
    }

    private final InputStream audioInputStream;
    private final OutputStream textOutputStream;
    private final InputStream resultStream;
    private SessionStatus status;

    public XFYASRSession(URI serverUri, InputStream audioStream) {
        super(serverUri);
        this.audioInputStream = audioStream;
        resultStream  = new PipedInputStream();
        try {
            textOutputStream = new PipedOutputStream((PipedInputStream) resultStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() {
        return resultStream;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        status = SessionStatus.RUNNING;
        logger.info("onOpen: " + handshakeData.getHttpStatus());
        Thread.startVirtualThread(()->{
            byte[] audioBuffer = new byte[AUDIO_BUFFER_SIZE];
            try {
                int size;
                boolean first = true;
                while ((size = audioInputStream.read(audioBuffer)) != -1) {
                    var content_ = new byte[AUDIO_BUFFER_SIZE];
                    System.arraycopy(audioBuffer, 0, content_, 0, size);
                    var requestContent = makeRequest(content_, first);
                    logger.info("content: " + requestContent);
                    first = false;
                    send(requestContent);
                    Thread.sleep(40);
                }
                var lastRequest = makeEndRequest();
                logger.info("content: " + lastRequest);
                send(lastRequest);
                status = SessionStatus.WAITING_FOR_REMOTE;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onMessage(String message) {
        logger.info("message " + message);
        var om = new ObjectMapper();
        try {
            var obj = om.readValue(message, Response.class);
            textOutputStream.write(getFragmentText(obj.data().result()).getBytes(StandardCharsets.UTF_8));
            if (obj.data().status() == 2  || obj.data().result().ls()) {
                textOutputStream.flush();
                textOutputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFragmentText(Result result) {
        StringBuilder sb = new StringBuilder();
        for (var ws : result.ws()) {
            for (var cw : ws.cw()) {
                sb.append(cw.w());
            }
        }

        return sb.toString();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        status = SessionStatus.FINISHED;
    }

    @Override
    public void onError(Exception ex) {
        close();
        status = SessionStatus.FINISHED;
    }

    private String makeRequest(byte[] content, boolean isFirstFrame) {
        var builder = makeRequestBuilder();
        builder.data.status = isFirstFrame ? 0 : 1;
        builder.data.audio = Base64.getEncoder().encodeToString(content);
        var om = new ObjectMapper();
        try {
            return om.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String makeEndRequest() {
        var builder = makeRequestBuilder();
        builder.data.status = 2;
        builder.data.audio = "";
        var om = new ObjectMapper();
        try {
            return om.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Request.Builder makeRequestBuilder() {
        Request.Builder builder = new Request.Builder();
        builder.common.app_id = KeyManager.XFY_APPID;
        builder.business.language = "zh_cn";
        builder.business.domain = "iat";
        builder.business.accent = "mandarin";
        builder.business.pd = "game";
        builder.business.ptt = 1;
        builder.business.rlang = "zh-cn";
        builder.business.vinfo = 0;
        builder.business.nunum = 1;
        builder.business.speex_size = 0;
        builder.business.nbest = 1;     //暂时先用一个候选句子
        builder.business.wbest = 1;     //暂时先用一个候选词
        builder.data.format = "audio/L16;rate=16000";
        builder.data.encoding = "raw";

        return builder;
    }
}
