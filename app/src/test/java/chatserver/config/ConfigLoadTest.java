package chatserver.config;

import chatserver.config.robot.Robot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ConfigLoadTest {
    private static final Logger logger = Logger.getLogger(ConfigLoadTest.class.getName());
    @Test
    public void loadConfigTest() {
        var rootPath = Paths.get("C:\\Users\\kip\\Documents\\workspace\\chatserver\\configdir");
        Config config = Config.getInstance();
        config.reload(rootPath);
        List<Robot> robots = config.getRobots();
        Assertions.assertEquals(2, robots.size());
        logger.info("robot count " + robots.size());
        Robot robot = robots.get(1);
        var configName = robot.configName();
        Assertions.assertEquals("aya", configName);
        var messages = robot.prompt().getMessage(HashMap.newHashMap(0));
        for (Prompt.PromptMessage(Prompt.PromptRole role, String content) : messages) {
            logger.info(role + ":" + content);
        }

        var robot2 = robots.get(0);
        var name = robot2.configName();
        Assertions.assertEquals("bob", name);
    }
}
