package chatserver.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;

public class IniFileReader {
    private final Properties properties = new Properties();

    public static IniFileReader fromFile(Path filePath) {
        var iniReader = new IniFileReader();
        try (var fileInputReader = new InputStreamReader(new FileInputStream(filePath.toFile()), StandardCharsets.UTF_8)) {
            iniReader.properties.load(fileInputReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return iniReader;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String put(String key, String value) {
        return (String)properties.setProperty(key, value);
    }
}
