package chatserver.config;

import chatserver.config.robot.Robot;
import chatserver.config.robot.RobotConfig;
import chatserver.config.skill.Skill;
import chatserver.config.skill.SkillConfig;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Config {
    private static final Logger logger = Logger.getLogger(Config.class.getName());

    private static class ConfigHolder {
        public static final Config INSTANCE = new Config();
    }

    private final Map<String, Robot> robots;
    private final Map<String, Skill> skills;

    private final Map<String, List<String>> skillDependencies;
    public Path currentReloadPath;

    public static Config getInstance() {
        return ConfigHolder.INSTANCE;
    }

    public List<Robot> getRobots() {
        return new ArrayList<>(robots.values());
    }

    // private static final Set<String> LEVEL_1_DIRS = new HashSet<>(Arrays.asList("bots", "skills"));

    private Config() {
        robots = new HashMap<>();
        skills = new HashMap<>();
        skillDependencies = new HashMap<>();
    }

    public Robot getRobotByName(String robotName) {
        return robots.get(robotName);
    }

    public static UpdateKind parseUpdateKind(WatchEvent.Kind<Path> kind) {
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            return UpdateKind.CREATE;
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            return UpdateKind.MODIFY;
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            return UpdateKind.DELETE;
        } else {
            throw new IllegalArgumentException("unsupported kind " + kind.name());
        }
    }

    synchronized public void reload(Path root, Path configPath, WatchEvent.Kind<Path> kind) {
        logger.info("reload file " + configPath.toAbsolutePath());
        var diff = calculateDiff(root, configPath);
        if (diff.isEmpty()) {
            return;
        }
        currentReloadPath = configPath;
        var topLevel = diff.get(0);
        switch (topLevel) {
            case "bots" -> updateRobot(diff.subList(1, diff.size()), parseUpdateKind(kind));
            case "skills" -> updateSkill(diff.subList(1, diff.size()), parseUpdateKind(kind));
        }
        currentReloadPath = null;
    }

    private void updateRobot(List<String> configPath, UpdateKind updateKind) {
        if (configPath.isEmpty()) {
            return;
        }

        var robotName = configPath.get(0);
        if (configPath.size() == 1 && updateKind == UpdateKind.DELETE) {
            removeRobot(robotName);
            return;
        }

        var robotConfig = makesureRobot(robotName);
        robotConfig.update(configPath.subList(1, configPath.size()), updateKind);
    }

    private void updateSkill(List<String> configPath, UpdateKind updateKind) {
        if (configPath.isEmpty()) {
            return;
        }
        var skillName = configPath.get(0);
        if (configPath.size() == 1 && updateKind == UpdateKind.DELETE) {
            removeSkill(skillName);
            return;
        }
        var skillConfig = makesureSkill(skillName);
        skillConfig.update(configPath.subList(1, configPath.size()), updateKind);
    }

    synchronized public void reload(Path root) {
        stash();
        try {
            if (!Files.exists(root) || !Files.isDirectory(root)) {
                throw new RuntimeException("config dir %s don't exist or not a directory".formatted(root));
            }

            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    reload(root, file, StandardWatchEventKinds.ENTRY_CREATE);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            popStash();
        }
    }

    private Robot makesureRobot(String name) {
        if (robots.containsKey(name)) {
            return robots.get(name);
        } else {
            return createRobot(name);
        }
    }

    private Skill makesureSkill(String name) {
        if (skills.containsKey(name)) {
            return skills.get(name);
        }
        return createSkill(name);
    }

    private Robot createRobot(String name) {
        var robot = new RobotConfig(name);
        robots.put(name, robot);
        return robot;
    }

    private Skill createSkill(String name) {
        var skill = new SkillConfig(name);
        skills.put(name, skill);
        skillDependencies.put(name, new ArrayList<>());
        return skill;
    }

    private void removeRobot(String name) {
        var removedRobot = robots.remove(name);
    }

    private void removeSkill(String name) {
        skills.remove(name);
    }

    private List<String> calculateDiff(Path root, Path current) {
        current = current.toAbsolutePath();
        var diff = root.relativize(current);
        var result = new ArrayList<String>();
        for (int i = 0; i < diff.getNameCount(); i++) {
            result.add(diff.getName(i).toString());
        }
        return result;
    }

    private void stash() {
    }

    private void popStash() {

    }
}
