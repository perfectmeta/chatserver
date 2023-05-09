package chatserver.util;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    public static boolean checkEmailAddressRule(String content) {
        return EMAIL_PATTERN.matcher(content).matches();
    }

    public static boolean checkPhoneNumberRule(String content) {
        return PHONE_PATTERN.matcher(content).matches();
    }
}
