package third.tts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.handshake.ServerHandshake;

import java.io.*;
import java.net.URI;
import java.util.Base64;
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
        send(generateRequest(textContent));
        sessionStatus = SessionStatus.WAITING_FOR_REMOTE;
    }

    @Override
    public void onMessage(String message) {
        ObjectMapper om = new ObjectMapper();
        try {
            var obj = om.readValue(message, third.tts.entity.stream.res.Response.class);
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
        return content;
    }
}
