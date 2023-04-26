package chatserver.security.authentication.xfy;

import okhttp3.HttpUrl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class AuthURLEncoder {
    public static final Logger logger = Logger.getLogger(AuthURLEncoder.class.getName());
    public static String encodeXFYAuthorUrl(String hostUrl, String apiKey, String apiSecret, String method) {
        URI url;
        try {
            url = new URI(hostUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        String builder = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                method +  " " + url.getPath() + " HTTP/1.1";
        var charset = StandardCharsets.UTF_8;
        String sha;
        try {
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            Mac mac = Mac.getInstance("hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.getBytes(charset));
            sha = Base64.getEncoder().encodeToString(hexDigits);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException(e);
        }

        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                apiKey, "hmac-sha256", "host date request-line", sha);
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().
                addQueryParameter("host", url.getHost()).
                addQueryParameter("date", date).
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).
                build();
        return httpUrl.toString();
    }
}
