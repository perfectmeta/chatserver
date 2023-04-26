package third.asr;

import security.KeyManager;
import security.authentication.xfy.AuthURLEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.logging.Logger;

public class XFYasr {
    public static final Logger logger = Logger.getLogger(XFYasr.class.getName());
    public static final String asrHost = "https://iat-api.xfyun.cn/v2/iat";

    public static ASRSession makeSession() {
        String url;
        try {
            logger.info(String.format("key:%s, secret:%s", KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET));
            url = AuthURLEncoder.encodeXFYAuthorUrl(asrHost, KeyManager.XFY_API_KEY, KeyManager.XFY_API_SECRET, "GET");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        url = url.replace("https:", "wss:");
        XFYASRSession session = null;
        try {
            session = new XFYASRSession(URI.create(url), new FileInputStream(new File("")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        session.connect();
        return session;
    }
}
