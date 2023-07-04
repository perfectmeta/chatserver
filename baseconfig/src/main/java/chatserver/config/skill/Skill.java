package chatserver.config.skill;

import chatserver.config.UpdateKind;

import java.nio.file.Path;
import java.util.List;

public interface Skill {
    void update(Path path);
}
