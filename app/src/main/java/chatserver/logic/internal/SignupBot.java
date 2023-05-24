package chatserver.logic.internal;

import chatserver.entity.User;
import chatserver.entity.UserCategory;
import chatserver.service.UserCategoryService;
import chatserver.service.UserService;
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
        user.setUserCategory(category);
        user.setGender(userCategory.getGender());
        user.setNickName(userCategory.getUserCategoryName());
        user = userService.addUser(user);
        return user;
    }
}
