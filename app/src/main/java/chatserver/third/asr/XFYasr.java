package chatserver.third.asr;

import chatserver.security.Secrets;
import chatserver.security.authentication.xfy.AuthURLEncoder;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class XFYasr {
    public static final Logger logger = Logger.getLogger(XFYasr.class.getName());
    public static final String asrHost = "https://iat-api.xfyun.cn/v2/iat";

    static {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + Secrets.XFY_APPID + " ");
    }

    public static BlockingQueue<Object> makeSession(InputStream audioStream) {
        String url;
        try {
            url = AuthURLEncoder.encodeXFYAuthorUrl(asrHost, Secrets.XFY_API_KEY, Secrets.XFY_API_SECRET, "GET");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        url = url.replace("https:", "wss:");
        XFYASRSession session;
        session = new XFYASRSession(URI.create(url), audioStream);
        session.connect();
        return session.getInputStream();
    }
}
