package chatserver.config.robots;

public class RobotConfig implements Robot {
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
}
