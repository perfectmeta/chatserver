package chatserver.third.asr;

import chatserver.security.KeyManager;
import chatserver.security.authentication.xfy.AuthURLEncoder;

import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

public class XFYasr {
    public static final Logger logger = Logger.getLogger(XFYasr.class.getName());
    public static final String asrHost = "https://iat-api.xfyun.cn/v2/iat";

    public static InputStream makeSession(InputStream audioStream) {
        String url;
        try {
            logger.info(String.format("key:%s, secret:%s", KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET));
            url = AuthURLEncoder.encodeXFYAuthorUrl(asrHost, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "GET");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        url = url.replace("https:", "wss:");
        XFYASRSession session = null;
        session = new XFYASRSession(URI.create(url), audioStream);
        session.connect();
        return session.getInputStream();
    }
}
