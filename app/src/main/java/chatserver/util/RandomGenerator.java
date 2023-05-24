package chatserver.util;

import java.util.Random;

public class RandomGenerator {
    private static final String PHONE_NUMBER_PREFIX = "+86";
    public static String randomPhoneNumber() {
        StringBuilder sb = new StringBuilder(PHONE_NUMBER_PREFIX);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
