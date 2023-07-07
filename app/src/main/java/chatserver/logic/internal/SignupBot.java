package chatserver.logic.internal;

import chatserver.config.RobotConfig;
import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.service.UserService;
import chatserver.util.RandomGenerator;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.logging.Logger;

@Component
public class SignupBot {
    private static final Logger logger = Logger.getLogger(SignupBot.class.getName());
    private final UserService userService;

    public SignupBot(UserService userService) {
        this.userService = userService;
    }

    public User signupFor(RobotConfig robot) {
        Objects.requireNonNull(robot);
        User user = new User();
        user.setUserType(EUserType.BOT);
        user.setBotId(robot.getId());
        user.setGender(robot.getGender());
        user.setNickName(robot.getId());
        user.setHeadIconUrl(robot.getHeadIcon());
        user.setPhone(RandomGenerator.randomPhoneNumber());
        user.setEmail(RandomGenerator.randomEmailAddress());
        user = userService.addUser(user);
        return user;
    }
}
