package chatserver.config.robots;

public interface Robot {
    String configName();
    String prompt();
    boolean isValid();
}
