package chatserver.config;

import chatserver.config.robots.Robot;
import chatserver.config.skill.Skill;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Objects;

public class Config {

    private static class ConfigHolder {
        public static final Config INSTANCE = new Config();
    }

    private Map<String, Robot> robots;
    private Map<String, Skill> skills;

    public static Config getInstance() {
        return ConfigHolder.INSTANCE;
    }

    public void reload(Path root, Path configPath, WatchEvent.Kind<Path> kind) {

    }

    public void reload(Path root) {
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

    private void stash() {
    }

    private void popStash() {

    }
}
