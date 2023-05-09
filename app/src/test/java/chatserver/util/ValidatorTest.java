package chatserver.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
    @Test
    void phoneValidatorTest() {
        var phone = "+8618585858585";
        Assertions.assertTrue(Validator.checkPhoneNumberRule(phone));
    }
}
