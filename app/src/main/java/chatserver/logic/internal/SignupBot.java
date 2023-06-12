package chatserver.logic.internal;

import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.entity.UserCategory;
import chatserver.service.UserCategoryService;
import chatserver.service.UserService;
import chatserver.util.RandomGenerator;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SignupBot {
    private static final Logger logger = Logger.getLogger(SignupBot.class.getName());
    private final UserService userService;
    private final UserCategoryService userCategoryService;

    public SignupBot(UserService userService, UserCategoryService userCategoryService) {
        this.userService = userService;
        this.userCategoryService = userCategoryService;
    }

    public User signupFor(int category) {
        assert category != 0: "Signup Bot must have a category nonzero, zero represent a human";
        UserCategory userCategory = userCategoryService.findUserCategoryById(category);
        if (userCategory == null) {
            logger.warning("User category %d not exist".formatted(category));
            return null;
        }
        User user = new User();
        user.setUserType(EUserType.BOT);
        user.setBotId(userCategory.getUserCategoryName());
        user.setGender(userCategory.getGender());
        user.setNickName(userCategory.getUserCategoryName());
        user.setPhone(RandomGenerator.randomPhoneNumber());
        user.setEmail(RandomGenerator.randomEmailAddress());
        user = userService.addUser(user);
        return user;
    }
}
