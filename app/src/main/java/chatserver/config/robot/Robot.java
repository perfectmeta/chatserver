package chatserver.config.robot;

import chatserver.config.Prompt;
import chatserver.config.UpdateKind;
import chatserver.config.skill.Skill;

import java.util.List;

public interface Robot {
    String configName();
    Prompt prompt();
    boolean isValid();
    void update(List<String> strings, UpdateKind kind);
}
