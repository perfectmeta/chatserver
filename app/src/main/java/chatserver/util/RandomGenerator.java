package chatserver.util;

import java.util.Random;

public class RandomGenerator {
    private static final String PHONE_NUMBER_REGION_PREFIX = "+86";
    private static final String[] PHONE_NUMBER_MNO_PREFIXES = {"117", "131", "135", "137", "151", "186"};
    public static String randomPhoneNumber() {
        StringBuilder sb = new StringBuilder(PHONE_NUMBER_REGION_PREFIX);
        Random random = new Random();
        sb.append(PHONE_NUMBER_MNO_PREFIXES[random.nextInt(PHONE_NUMBER_MNO_PREFIXES.length)]);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
