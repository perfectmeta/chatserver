package chatserver.config.robot;

import chatserver.config.Prompt;
import chatserver.config.UpdateKind;

import java.util.List;

public interface Robot {
    String configName();
    Prompt prompt();
    String greeting();
    String speaker();
    String model();
    boolean isValid();
    int getGender();
}
