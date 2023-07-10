package chatserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());

    public static volatile ConfigManager instance = new ConfigManager();

    private final Map<String, RobotConfig> robots = new HashMap<>();

    public static ConfigManager getInstance() {
        return instance;
    }

    public Map<String, RobotConfig> getRobots() {
        return robots;
    }

    public RobotConfig getRobotByName(String robotName) {
        return robots.get(robotName);
    }


    public static void reload(Path root) {
        var newManager = new ConfigManager();

        if (!Files.exists(root)) {
            logger.warning(root + " reload. path not exist");
            return;
        }

        if (!Files.isDirectory(root)) {
            logger.warning(root + " reload. path not directory");
            return;
        }

        Path bots_dir = root.resolve("bots");
        if (!Files.isDirectory(bots_dir)) {
            logger.warning(root + " reload. no bots directory");
            return;
        }

        try (var paths = Files.list(bots_dir)) {
            for (Path path : paths.filter(Files::isDirectory).toList()) {
                newManager.readRobot(path);
            }
        } catch (IOException e) {
            logger.warning(root + " reload. exception: " + e.getMessage());
        }

        instance = newManager;
    }

    private static class RobotProfile {
        public String name;
        public String description;
        public String model;
        public String speaker;
        public String greeting;
        public String head;
        public String artistModel;
    }

    private void readRobot(Path path) {
        String id = path.getName(path.getNameCount() - 1).toString();

        Path profilePath = path.resolve("profile.json");


        if (!Files.exists(profilePath)) {
            logger.warning(path + " profile.json not exist");
            return;
        }


        ObjectMapper objectMapper = new ObjectMapper();
        RobotProfile profile;
        try {
            profile = objectMapper.readValue(profilePath.toFile(), RobotProfile.class);
        } catch (IOException e) {
            logger.warning(path + " profile.json read exception: " + e.getMessage());
            return;
        }

        String promptStr = "";
        Path promptPath = path.resolve("prompt.txt");
        if (Files.exists(promptPath)) {
            try {
                promptStr = Files.readString(promptPath, StandardCharsets.UTF_8);
            } catch (IOException e) {
                logger.warning(path + " prompt.txt read exception" + e.getMessage());
                return;
            }
        }

        RobotConfig rc = new RobotConfig(id,
                profile.name,
                profile.description,
                profile.model,
                profile.speaker,
                profile.greeting,
                promptStr,
                profile.head,
                profile.artistModel);
        robots.put(rc.getId(), rc);
    }

}
