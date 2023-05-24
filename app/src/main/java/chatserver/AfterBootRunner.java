package chatserver;

import chatserver.entity.User;
import chatserver.entity.UserCategory;
import chatserver.service.UserCategoryService;
import chatserver.service.UserService;
import chatserver.util.RandomGenerator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class AfterBootRunner implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(AfterBootRunner.class.getName());
    private final UserCategoryService userCategoryService;
    private final UserService userService;

    public AfterBootRunner(UserCategoryService userCategoryService,
                           UserService userService) {
        this.userCategoryService = userCategoryService;
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
        var humanCategory = userCategoryService.findUserCategoryById(0);
        if (humanCategory != null) {
            return;
        }
        UserCategory uc = new UserCategory();
        uc.setUserCategoryId(0);
        uc.setUserCategoryName("");
        uc.setGender(0);
        uc.setTags("");
        uc.setUserType(0);
        uc.setPrompt("");
        uc.setDescription("人类条目");
        userCategoryService.addUserCategory(uc);
    }

    private void checkAndRegisterUser() {
        var botCategories = userCategoryService.findAllBotUserCategories();
        var botUsers = userService.findAllBotUsers();
        var botUserCats = botUsers.stream().map(User::getUserCategory).collect(Collectors.toSet());
        var needRegisters = new ArrayList<User>();
        for (var cat : botCategories) {
            if (botUserCats.contains((int)cat.getUserCategoryId())) {
                continue;
            }
            User user = new User();
            user.setUserCategory((int)cat.getUserCategoryId());
            user.setPhone(RandomGenerator.randomPhoneNumber());
            user.setEmail(cat.getUserCategoryName() + "_bot@pwrd.com");
            user.setNickName(cat.getUserCategoryName());
            user.setHeadIconUrl("/headicon/12354");
            needRegisters.add(user);
        }
        if (needRegisters.size() > 0) {
            userService.addUsers(needRegisters);
        }
    }
}
