package chatserver.config.robot;

import chatserver.config.UpdateKind;
import chatserver.config.skill.Skill;

import java.util.List;

public interface Robot {
    String configName();
    String prompt();
    boolean isValid();
    void update(List<String> strings, UpdateKind kind);
}
