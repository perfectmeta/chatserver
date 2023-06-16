package chatserver.config.robot;

import chatserver.config.Config;
import chatserver.config.UpdateKind;
import chatserver.config.skill.Skill;
import com.google.common.base.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RobotConfig implements Robot {
    private final String configName;
    private String prompt = "";
    private String greetings = "";
    private final List<String> model = new ArrayList<>();

    private RobotConfigModifier modifier;
    public RobotConfig(String name) {
        configName = name;
        modifier = this.new RobotConfigModifier();
    }

    class RobotConfigModifier {
        public void update(List<String> configPath, UpdateKind kind) {
            if (configPath.isEmpty()) {
                return;
            }

            var top = configPath.get(0);
            switch (top) {
                case "greetings.txt" -> updateGreetings(kind);
                case "model.txt" -> updateModel(kind);
                case "prompt.txt" -> updatePrompt(kind);
            }
        }

        private void updateGreetings(UpdateKind kind) {
            var filePath = Config.getInstance().currentReloadPath;
            if (kind == UpdateKind.DELETE) {
                RobotConfig.this.greetings = "";
                return;
            }

            try (var reader = new BufferedReader(new FileReader(filePath.toAbsolutePath().toFile()))) {
                var line = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!Strings.isNullOrEmpty(line)) {
                        greetings = line;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateModel(UpdateKind kind) {
            if (kind == UpdateKind.DELETE) {
                RobotConfig.this.model.clear();
                return;
            }

            RobotConfig.this.model.clear();
            try (var reader = new BufferedReader(new FileReader(Config.getInstance().currentReloadPath.toAbsolutePath().toFile()))) {
                var line = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!Strings.isNullOrEmpty(line)) {
                        RobotConfig.this.model.add(line);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updatePrompt(UpdateKind kind) {
            if (kind == UpdateKind.DELETE) {
                RobotConfig.this.prompt = "";
                return;
            }

            try (var reader = new BufferedReader(new FileReader(Config.getInstance().currentReloadPath.toAbsolutePath().toFile()))) {
                var line = "";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String configName() {
        return null;
    }

    @Override
    public String prompt() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void update(List<String> configPath, UpdateKind kind) {
        modifier.update(configPath, kind);
    }
}
