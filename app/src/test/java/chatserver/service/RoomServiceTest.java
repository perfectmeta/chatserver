package chatserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class RoomServiceTest {

    private final Logger logger = Logger.getLogger(RoomServiceTest.class.getName());

    @Autowired
    RoomService service;

    @Test
    public void getRoomMaxMessageIdTest() {
        var roomId = service.getRoomMaxMessageId(1);
        logger.info("RoomId: " + roomId);
        Assertions.assertTrue(roomId > 10);
    }
}
