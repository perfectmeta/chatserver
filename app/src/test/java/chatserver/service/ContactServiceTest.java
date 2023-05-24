package chatserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class ContactServiceTest {
    private static final Logger logger = Logger.getLogger(ContactService.class.getName());

    @Autowired
    ContactService contactService;

    @Test
    void getMemoriesTest() {
        var memories = contactService.getAllMemory(1, 2);
        Assertions.assertTrue(memories.size() == 1);
        logger.info(memories.get(0).toString());
    }

    @Test
    void deleteMemoryTest() {
        var deletedCnt = contactService.deleteMemoryByUserIdAndOtherUserIdAndMemoryId(1, 2, 2);
        Assertions.assertEquals(1, deletedCnt);
        logger.info("finish deleteMemoryTest ");
    }
}
