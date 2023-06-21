package chatserver;

import chatserver.config.Config;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class AfterBootRunner implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(AfterBootRunner.class.getName());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Now start running initial jobs ... ");
        checkAndRegisterUserCategory();
        checkAndRegisterUser();
        loadConfigDir();
        logger.info("initial jobs finished ... ");
    }

    private void checkAndRegisterUserCategory() {
    }

    private void checkAndRegisterUser() {
    }

    private void loadConfigDir() {
        Config config = Config.getInstance();
        var configDir = System.getenv("chatserver_config_dir");
        config.reload(Paths.get(configDir));
        logger.info("reload config from %s finished".formatted(configDir));
    }
}
