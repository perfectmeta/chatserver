package util.websocket;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketTLSClient extends org.java_websocket.client.WebSocketClient {
    public WebSocketTLSClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        if (handshakedata.getHttpStatus() != 101) {
            close();
            return;
        }

        Thread.startVirtualThread(()->{

        });
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
