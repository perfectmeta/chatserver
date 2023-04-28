package chatserver.asr;

import chatserver.third.asr.XFYasr;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class XFYasrTest {
    @Test
    public void asrTest() {
        try {
            var audioPath = Paths.get("src/test/resources/iat_speex_wideband_8k.speex").toAbsolutePath();
            System.out.println(audioPath);
            FileInputStream fis = new FileInputStream(audioPath.toFile());
            var ins = XFYasr.makeSession(fis);
            byte[] bytes = ins.readAllBytes();
            var content = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
