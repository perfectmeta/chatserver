package chatserver.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashMap;

public class ConfigLoadTest {
    @Test
    public void loadConfigTest() {
        var rootPath = Paths.get("../configdir");

        ConfigManager.reload(rootPath);
        var robots = ConfigManager.getInstance().getRobots();
        System.out.println("robot count " + robots.size());

        RobotConfig robot = robots.values().iterator().next();
        var configName = robot.getId();
        Assertions.assertEquals("aya", configName);
        var messages = robot.getPrompt().getMessage(HashMap.newHashMap(0));
        for (PromptConfig.PromptMessage(PromptConfig.Role role, String content) : messages) {
            System.out.println(role + ":" + content);
        }
    }
}
