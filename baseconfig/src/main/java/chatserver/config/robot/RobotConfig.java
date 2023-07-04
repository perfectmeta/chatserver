package chatserver.config.robot;

import chatserver.config.Config;
import chatserver.config.Prompt;
import chatserver.config.UpdateKind;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RobotConfig implements Robot {
    private final String configName;
    private Prompt prompt = null;
    private String greetings = "";
    private String speaker = "";
    private final List<String> skills = new ArrayList<>();
    private final List<String> model = new ArrayList<>();   // 聊天，记忆，摘要等，使用不同model即可

    private final RobotConfigModifier modifier;
    public RobotConfig(String name) {
        configName = name;
        modifier = this.new RobotConfigModifier();
    }

    public RobotConfigModifier getModifier() {
        return modifier;
    }

    public class RobotConfigModifier {
        public void update(Path robotPath) {
            updatePrompt(robotPath.resolve("prompt.txt"));
            updateBaseInfo(robotPath.resolve("profile.json"));
        }

        private void updateBaseInfo(Path path) {
            ObjectMapper objectMapper = new ObjectMapper();
            RobotBaseJO robotJo;
            try {
                robotJo = objectMapper.readValue(path.toAbsolutePath().toFile(), RobotBaseJO.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            updateModel();
            RobotConfig.this.greetings = robotJo.greeting;
            RobotConfig.this.speaker = robotJo.speaker;
        }

        private void updateModel() {
            RobotConfig.this.model.clear();
            if (Strings.isNullOrEmpty("\"gpt-3.5-turbo\"")) {
                return;
            }
            var models = "\"gpt-3.5-turbo\"".split(",");
            for (var m : models) {
                m = m.trim();
                if (!Strings.isNullOrEmpty(m)) {
                    RobotConfig.this.model.add(m);
                }
            }
        }

        private void updatePrompt(Path path) {
            var promptBuilder = Prompt.newBuilder();
            try (var reader = new BufferedReader(new FileReader(path.toAbsolutePath().toFile()))) {
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
    public String greeting() {
        return greetings;
    }

    @Override
    public String speaker() {
        return speaker;
    }

    @Override
    public String model() {
        if (model.size() > 0) {
            return model.get(0);
        }
        return "gpt-3.5-turbo";
    }

    @Override
    public boolean isValid() {
        return !Strings.isNullOrEmpty(greetings) && prompt != null;
    }

    @Override
    public int getGender() {
        return 1;
    }
}
