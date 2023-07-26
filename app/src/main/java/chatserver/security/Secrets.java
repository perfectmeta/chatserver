package chatserver.security;

import com.google.common.base.Strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Secrets {
    public static String XFY_APPID = "";
    public static String XFY_API_SECRET = "";
    public static String XFY_API_KEY = "";

    public static String ASR_KEY = "";

    public static String OPENAI_BASEURL = "";
    public static String OPENAI_KEY = "";

    public static String PWRDTTS_HOST = "";
    public static String PWRDTTS_DEFAULT_SPEAKER = "";

    public static String DATA_STATIC_DIR = "data/static";
    public static String DATA_HEAD_ICON_DIR = "data/headicon";


    static {
        load();
    }


    private static void load() {
        String secretFile = System.getenv("chatserver.secret");
        if (Strings.isNullOrEmpty(secretFile)) {
            secretFile = "config/secret.properties";
        }

        var properties = new Properties();
        try (var fileInputStream = new FileInputStream(secretFile)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XFY_APPID = properties.getProperty("tts.appid");
        XFY_API_KEY = properties.getProperty("tts.apikey");
        XFY_API_SECRET = properties.getProperty("tts.apisecret");

        ASR_KEY = properties.getProperty("asr.key");

        OPENAI_BASEURL = properties.getProperty("openai.base-url");
        OPENAI_KEY = properties.getProperty("openai.key");

        PWRDTTS_HOST = properties.getProperty("pwrdtts.host");
        PWRDTTS_DEFAULT_SPEAKER = properties.getProperty("pwrdtts.default-speaker");

        DATA_STATIC_DIR = properties.getProperty("data.static-dir");
        DATA_HEAD_ICON_DIR = properties.getProperty("data.head-icon-dir");

    }

}
