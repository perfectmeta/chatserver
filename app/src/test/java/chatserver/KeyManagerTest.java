package chatserver;

import org.junit.jupiter.api.Test;
import chatserver.security.KeyManager;

import static org.junit.jupiter.api.Assertions.*;

public class KeyManagerTest {

    @Test
    public void testKeyManager() {
        assertEquals("test_key", KeyManager.TEST_KEY);
    }
}
