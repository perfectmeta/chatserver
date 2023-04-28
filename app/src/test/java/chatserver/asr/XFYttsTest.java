package chatserver.asr;

import org.junit.jupiter.api.Test;
import chatserver.third.tts.XFYtts;

import java.io.FileOutputStream;
import java.io.IOException;

public class XFYttsTest {
    @Test
    public void ttsStreamTest() throws IOException {
        try (var inputStream = XFYtts.makeSession("你好,中国");
             var fos = new FileOutputStream("testAudio")) {
            byte[] content = inputStream.readAllBytes();
            fos.write(content);
            fos.flush();
            System.out.println(content.length);
        }
    }
}
