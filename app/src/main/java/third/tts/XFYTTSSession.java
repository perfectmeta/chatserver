package third.tts;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class XFYTTSSession extends org.java_websocket.client.WebSocketClient {
    public XFYTTSSession(URI uri) {
        super(uri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
