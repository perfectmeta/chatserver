package chatserver.config.skill;

import chatserver.config.UpdateKind;

import java.util.List;

public interface Skill {
    void update(List<String> strings, UpdateKind updateKind);
}
