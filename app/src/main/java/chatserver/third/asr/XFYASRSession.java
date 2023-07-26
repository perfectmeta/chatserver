package chatserver.third.asr;

import chatserver.security.Secrets;
import chatserver.third.asr.entity.stream.req.Request;
import chatserver.third.asr.entity.stream.res.Response;
import chatserver.util.StopSignal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
    private final BlockingQueue<Object> blockingQueue;
    private final Decoder decoder;

    @SuppressWarnings("unused")
    private SessionStatus status;

    public XFYASRSession(URI serverUri, InputStream audioStream) {
        super(serverUri);
        this.audioInputStream = audioStream;
        blockingQueue = new LinkedBlockingQueue<>();
        decoder = new Decoder(blockingQueue);
    }

    public BlockingQueue<Object> getInputStream() {
        return blockingQueue;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        status = SessionStatus.RUNNING;
        logger.info("onOpen: " + handshakeData.getHttpStatus());
        Thread.startVirtualThread(() -> {
            try {
                boolean first = true;
                byte[] buffer;
                while ((buffer = audioInputStream.readNBytes(AUDIO_BUFFER_SIZE * 10)).length > 0) {
                    var requestContent = makeRequest(buffer, first);
                    first = false;
                    send(requestContent);
                    // Thread.sleep(40);   //fixme 后面想个办法给去掉这个限制
                }
            } catch (IOException e) { // | InterruptedException e) {
                logger.warning(e.getMessage());
                e.printStackTrace();
            } finally {
                var lastRequest = makeEndRequest();
                logger.info("content last: " + lastRequest);
                send(lastRequest);
                status = SessionStatus.WAITING_FOR_REMOTE;
            }
        });
    }

    @Override
    public void onMessage(String message) {
        var om = new ObjectMapper();
        try {
            var obj = om.readValue(message, Response.class);
            logger.info("asr res:" + message);
            decoder.decode(obj.data().result());
            if (obj.data().status() == 2 || obj.data().result().ls()) {
                logger.warning("onMessage");
                blockingQueue.add(new StopSignal());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        status = SessionStatus.FINISHED;
        logger.warning("onClose");
    }

    @Override
    public void onError(Exception ex) {
        logger.warning("OnError:" + ex.getMessage());
        ex.printStackTrace();
        blockingQueue.add(new StopSignal());
        close();
        status = SessionStatus.FINISHED;
    }

    private String makeRequest(byte[] content, boolean isFirstFrame) {
        Request.Builder builder;
        if (isFirstFrame) {
            builder = makeRequestBuilder();
            builder.getData().status = 0;
        } else {
            builder = makeRequestDataBuilder();
            builder.getData().status = 1;
        }
        builder.getData().audio = Base64.getEncoder().encodeToString(content);
        var om = new ObjectMapper();
        try {
            return om.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String makeEndRequest() {
        var builder = makeRequestBuilder();
        builder.getData().status = 2;
        builder.getData().audio = "";
        var om = new ObjectMapper();
        try {
            return om.writeValueAsString(builder.build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Request.Builder makeRequestBuilder() {
        Request.Builder builder = new Request.Builder();
        builder.getCommon().app_id = Secrets.XFY_APPID;
        builder.getBusiness().language = "zh_cn";
        builder.getBusiness().domain = "iat";
        builder.getBusiness().accent = "mandarin";
        builder.getBusiness().pd = "game";
        builder.getBusiness().ptt = 1;
        builder.getBusiness().rlang = "zh-cn";
        builder.getBusiness().vinfo = 0;
        builder.getBusiness().nunum = 1;
        builder.getBusiness().speex_size = 0;
        builder.getBusiness().nbest = 1;     //暂时先用一个候选句子
        builder.getBusiness().wbest = 1;     //暂时先用一个候选词
        builder.getBusiness().dwa = "wpgs";
        builder.getData().format = "audio/L16;rate=16000";
        builder.getData().encoding = "raw";

        return builder;
    }

    private Request.Builder makeRequestDataBuilder() {
        Request.Builder builder = new Request.Builder();
        builder.getData().format = "audio/L16;rate=16000";
        builder.getData().encoding = "raw";

        return builder;
    }
}
