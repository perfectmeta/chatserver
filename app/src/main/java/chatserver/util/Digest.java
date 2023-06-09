package chatserver.util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Digest {

    public static String calculateMD5(byte[] content) {
        return calculateMD5(content, 0, content.length);
    }

    public static String calculateMD5(byte[] content, int offset, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content, offset, length);
            return toHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateMD5(ByteBuffer byteBuffer) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            return toHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        for (var b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }
}
