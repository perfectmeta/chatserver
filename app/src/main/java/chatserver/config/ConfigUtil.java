package chatserver.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtil {
    public static Prompt getPromptFromFile(Path path) {
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return null;
        }

        var prompt = new StringBuilder();
        try (var fileReader = new BufferedReader(new FileReader(path.toAbsolutePath().toFile()))) {
            var line = "";
            while ((line = fileReader.readLine()) != null) {
                //line =
            }
        } catch (IOException e) {
            return null;
        }
    }
}
