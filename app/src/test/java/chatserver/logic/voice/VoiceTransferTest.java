package chatserver.logic.voice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoiceTransferTest {
    @Test
    void clipFragment() {
        var content = VoiceTransfer.clipFragment("能够抚平人心中的创伤");
        assertEquals( "能够抚平人心中的创伤", content);
    }
}