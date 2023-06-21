package chatserver.config.robot;

import chatserver.config.Config;
import chatserver.config.Prompt;
import chatserver.config.UpdateKind;
import com.google.common.base.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RobotConfig implements Robot {
    private final String configName;
    private Prompt prompt = null;
    private String greetings = "";
    private final List<String> skills = new ArrayList<>();
    private final List<String> model = new ArrayList<>();

    private final RobotConfigModifier modifier;
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
                case "availableskills.txt" -> updateAvailableSkills(kind);
            }
        }

        private void updateGreetings(UpdateKind kind) {
            var filePath = Config.getInstance().currentReloadPath;
            if (kind == UpdateKind.DELETE) {
                RobotConfig.this.greetings = "";
                return;
            }

            try (var reader = new BufferedReader(new FileReader(filePath.toAbsolutePath().toFile()))) {
                var greetings = new StringBuilder();
                var line = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!Strings.isNullOrEmpty(line)) {
                        greetings.append(line);
                    }
                }
                RobotConfig.this.greetings = greetings.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateModel(UpdateKind kind) {
            RobotConfig.this.model.clear();
            if (kind == UpdateKind.DELETE) {
                return;
            }

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
                RobotConfig.this.prompt = null;
                return;
            }

            var promptBuilder = Prompt.newBuilder();
            try (var reader = new BufferedReader(new FileReader(Config.getInstance().currentReloadPath.toAbsolutePath().toFile()))) {
                var line = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith(Prompt.PromptRole.System.prefix())) {
                        var content = line.substring(Prompt.PromptRole.System.prefix().length());
                        promptBuilder.addMessage(Prompt.PromptRole.System, content);
                    } else if (line.startsWith(Prompt.PromptRole.User.prefix())) {
                        var content = line.substring(Prompt.PromptRole.User.prefix().length());
                        promptBuilder.addMessage(Prompt.PromptRole.User, content);
                    } else if (line.startsWith(Prompt.PromptRole.Assistant.prefix())) {
                        var content = line.substring(Prompt.PromptRole.Assistant.prefix().length());
                        promptBuilder.addMessage(Prompt.PromptRole.Assistant, content);
                    } else {
                        throw new IllegalStateException(line + " format error, must begin with a prefix S: or A: or U:");
                    }
                }
                RobotConfig.this.prompt = promptBuilder.build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateAvailableSkills(UpdateKind kind) {
            if (kind == UpdateKind.DELETE) {
                RobotConfig.this.skills.clear();
                return;
            }

            RobotConfig.this.skills.clear();

            try (var reader = new BufferedReader(new FileReader(Config.getInstance().currentReloadPath.toAbsolutePath().toFile()))) {
                var line = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!Strings.isNullOrEmpty(line)) {
                        RobotConfig.this.skills.add(line);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String configName() {
        return configName;
    }

    @Override
    public Prompt prompt() {
        return prompt;
    }

    @Override
    public boolean isValid() {
        return !Strings.isNullOrEmpty(greetings) && prompt != null;
    }

    @Override
    public void update(List<String> configPath, UpdateKind kind) {
        modifier.update(configPath, kind);
    }
}
