package chatserver.third.asr;

import chatserver.security.KeyManager;
import chatserver.security.authentication.xfy.AuthURLEncoder;
import com.iflytek.cloud.speech.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class XFYasr {
    public static final Logger logger = Logger.getLogger(XFYasr.class.getName());
    public static final String asrHost = "https://iat-api.xfyun.cn/v2/iat";

    public static final int AUDIO_WRITE_BUFFER_SIZE = 1024 * 1024;

    static {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + KeyManager.XFY_APPID + " ");
    }

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

    public static InputStream listen(InputStream audioStream) {
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer();
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.ASR_PTT, "1");
        recognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "audio/L16;rate=8000");
        recognizer.setParameter(SpeechConstant.TEXT_ENCODING, "UTF-8");

        PipedInputStream pis;
        PipedOutputStream pos;
        try {
            pos = new PipedOutputStream();
            pis = new PipedInputStream(pos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        recognizer.startListening(new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i) {

            }

            @Override
            public void onBeginOfSpeech() {
                logger.info("onBeginOfSpeech");
            }

            @Override
            public void onEndOfSpeech() {
                logger.info("onEndOfSpeech");
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                logger.info("onResult");
                if (b) {
                    finish();
                }
                try {
                    pos.write(recognizerResult.getResultString().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                logger.info("onError " + speechError.getErrorDesc());
                finish();
            }

            @Override
            public void onEvent(int i, int i1, int i2, String s) {
                logger.info("onEvent");
            }

            private void finish() {
                recognizer.destroy();
                try {
                    pos.flush();
                    pos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        byte[] buffer = new byte[AUDIO_WRITE_BUFFER_SIZE];
        int len;
        int offset = 0;
        try {
            while ((len = audioStream.read(buffer, offset, AUDIO_WRITE_BUFFER_SIZE - offset)) != -1) {
                offset += len;
                if (offset > 0) {
                    recognizer.writeAudio(buffer, 0, offset);
                    logger.info("write data " + offset);
                    offset = 0;
                }
            }
            if (offset > 0) {
                recognizer.writeAudio(buffer, 0, offset);
            }
            logger.info("write data finish" + offset);
            recognizer.stopListening();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pis;
    }
}
