package chatserver.security;

import com.google.common.base.Strings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class KeyManager {
    public static String XFY_APPID = "";
    public static String XFY_API_SECRET ="";
    public static String XFY_API_KEY ="";
    public static String TTS_KEY = "";
    public static String ASR_KEY = "";
    public static String OPENAI_KEY = "";
    public static String TEST_KEY = "";

    public static String TEACHER_PROMPT = "";

    static {
        loadKey();
    }

    public static void loadKey() {
        reload();
    }

    public static void reload() {
        reload(null);
    }

    public static void reload(String path) {
        var configFile = Strings.isNullOrEmpty(path) ? System.getenv("CHAT_KEY_FILE") : path;
        if (Strings.isNullOrEmpty(configFile)
                || !Files.exists(Paths.get(configFile).toAbsolutePath())
                || !Files.isRegularFile(Paths.get(configFile).toAbsolutePath())) {
            configFile = System.getProperty("user.dir") + File.separator + ".." + File.separator + "key.properties";
        }
        var properties = new Properties();
        try (var fileInputStream = new FileInputStream(configFile)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XFY_APPID = properties.getProperty("tts.appid");
        XFY_API_KEY = properties.getProperty("tts.apikey");
        XFY_API_SECRET = properties.getProperty("tts.apisecret");
        TTS_KEY = properties.getProperty("tts.key");
        ASR_KEY = properties.getProperty("asr.key");
        OPENAI_KEY = properties.getProperty("openai.key");
        TEST_KEY = properties.getProperty("test.key");
        TEACHER_PROMPT = properties.getProperty("teacher.prompt");

    }

}
