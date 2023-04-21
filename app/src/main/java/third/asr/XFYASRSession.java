package third.asr;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.logging.Logger;

public class XFYASRSession extends org.java_websocket.client.WebSocketClient implements ASRSession {
    public static final Logger logger = Logger.getLogger(XFYASRSession.class.getName());
    private boolean isValid = false;

    public XFYASRSession(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        isValid = true;
        logger.info("onOpen: " + handshakedata.getHttpStatus());
    }

    @Override
    public void onMessage(String message) {
        logger.info("message" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("close " + reason);
        isValid = false;
    }

    @Override
    public void onError(Exception ex) {
        logger.info(ex.getMessage());
        close();
        isValid = false;
    }
}
