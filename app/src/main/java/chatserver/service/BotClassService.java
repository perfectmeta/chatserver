package chatserver.service;

import chatserver.dao.BotClass;
import chatserver.dao.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotClassService {

    private final UserCategoryRepository userCategory;

    public BotClassService(UserCategoryRepository userCategory) {
        this.userCategory = userCategory;
    }

    public List<BotClass> getAllBotClasses() {
        return userCategory.findAll();
    }

    public BotClass findBotClassById(long id) {
        return userCategory.findBotClassByBotClassId(id);
    }
}
