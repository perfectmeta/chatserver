package chatserver.asr;

import chatserver.third.asr.XFYasr;
import chatserver.util.StopSignal;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class XFYasrTest {
    private static final Logger logger = Logger.getLogger(XFYasrTest.class.getName());
    @Test
    public void asrTest() throws IOException, InterruptedException {
        var audioPath = Paths.get("src/test/resources/iat_pcm_16k.pcm").toAbsolutePath();
        logger.info(audioPath.toString());
        FileInputStream fis = new FileInputStream(audioPath.toFile());
        var ins = XFYasr.makeSession(fis);

        while(true){
            var obj = ins.take();
            if(obj instanceof StopSignal){
                break;
            }
        }
//        byte[] bytes = ins.readAllBytes();
//        var content = new String(bytes, StandardCharsets.UTF_8);
//        logger.info("result size: " + bytes.length);
//        logger.info("result: " + content);
//        Assertions.assertEquals(content, "语音听写可以将语音转为文字。");
    }
}
