package chatserver.third.tts;

import chatserver.third.tts.entity.stream.req.Request;
import chatserver.third.tts.entity.stream.res.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.handshake.ServerHandshake;
import chatserver.security.KeyManager;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;


public class XFYTTSSession extends org.java_websocket.client.WebSocketClient {
    public static final Logger logger = Logger.getLogger(XFYTTSSession.class.getName());

    enum SessionStatus {
        CREATE,
        RUNNING,
        WAITING_FOR_REMOTE,
        FINISHED
    }

    private SessionStatus sessionStatus;
    private final InputStream inputStream;
    private final OutputStream resultStream;
    private final String textContent;

    public XFYTTSSession(URI uri, String content) {
        super(uri);
        textContent = content;
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        try {
            inputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resultStream = pipedOutputStream;
        sessionStatus = SessionStatus.CREATE;
    }

    @SuppressWarnings("unused")
    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        sessionStatus = SessionStatus.RUNNING;
        if (handshakedata.getHttpStatus()!=101) {
            logger.info(handshakedata.getHttpStatusMessage());
            sessionStatus = SessionStatus.FINISHED;
            return;
        }
        var json = generateRequest(textContent);
        logger.info(json);
        send(json);
        sessionStatus = SessionStatus.WAITING_FOR_REMOTE;
    }

    @Override
    public void onMessage(String message) {
        ObjectMapper om = new ObjectMapper();
        try {
            logger.info(message);
            var obj = om.readValue(message, Response.class);
            if (Objects.isNull(obj.data())) {
                logger.warning("result have no data field, raw message: " + message);
                return;
            }
            var status = obj.data().status();
            var audio = Base64.getDecoder().decode(obj.data().audio());
            resultStream.write(audio);
            if (status == 2) {
                resultStream.flush();
                sessionStatus = SessionStatus.FINISHED;
                close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        try {
            resultStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(Exception ex) {
        try {
            resultStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private String generateRequest(String content) {
        Request.Builder request = new Request.Builder();
        request.common.app_id = KeyManager.XFY_APPID;
        request.business.aue = "lame";
        request.business.sfl = 1;
        request.business.auf = "audio/L16;rate=8000";
        request.business.vcn = "xiaoyan";
        request.business.speed = 50;
        request.business.volume = 50;
        request.business.pitch = 50;
        request.business.bgs = 0;
        request.business.tte = "UTF8";
        request.business.reg = "0";
        request.business.rdn = "0";
        request.data.text = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        request.data.status = 2;

        var om = new ObjectMapper();
        try {
            return om.writeValueAsString(request.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
