package chatserver;

import chatserver.config.ConfigManager;
import chatserver.util.FileWatcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class AfterBootRunner implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(AfterBootRunner.class.getName());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadConfigDir();
    }

    private void loadConfigDir() {
        String configDir = System.getenv("chatserver_config_dir");
        Path path = Paths.get(configDir);

        ConfigManager.reload(path);
        logger.info("load config finish: %s".formatted(configDir));

        FileWatcher configChangeWatcher = new FileWatcher(path.resolve("changelist.txt"));
        configChangeWatcher.watch(() -> {
            ConfigManager.reload(path);
            logger.info("reload config finish: %s".formatted(configDir));
        });
    }
}
