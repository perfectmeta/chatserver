package third.asr;

import org.junit.jupiter.api.Test;
import chatserver.third.tts.XFYtts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XFYttsTest {
    @Test
    public void ttsStreamTest() {
        var inputStream = XFYtts.makeSession("你好,中国");
        var file = new File("testAudio");
        try (var fos = new FileOutputStream(file)) {
            var content = inputStream.readAllBytes();
            fos.write(content);
            fos.flush();
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
