package chatserver.third.tts;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class PwrdttsTest {

    @Test
    public void testTts() throws IOException {
        byte[] bytes = Pwrdtts.tts("由于照片捏脸预训练的分类器需要和 角色发型、妆容配置保持一致", "zhy");
        if (bytes != null){
            Path p = Paths.get("tts.mp3");
            if (!Files.exists(p)){
                Files.createFile(p);
            }

            Files.write(p, bytes);
        }
    }

}