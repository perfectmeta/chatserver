package chatserver;

import chatserver.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AfterBootRunner implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(AfterBootRunner.class.getName());
    private final UserService userService;

    public AfterBootRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Now start running initial jobs ... ");
        checkAndRegisterUserCategory();
        checkAndRegisterUser();
        logger.info("initial jobs finished ... ");
    }

    private void checkAndRegisterUserCategory() {
    }

    private void checkAndRegisterUser() {
    }

    private void loadConfigDir() {

    }
}
