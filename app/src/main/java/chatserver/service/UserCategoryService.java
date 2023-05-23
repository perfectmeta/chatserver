package chatserver.service;

import chatserver.dao.UserCategory;
import chatserver.dao.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategory;

    public UserCategoryService(UserCategoryRepository userCategory) {
        this.userCategory = userCategory;
    }

    public List<UserCategory> getAllUserCategories() {
        return userCategory.findAll();
    }

    public UserCategory findUserCategoryById(long id) {
        return userCategory.findByUserCategoryId(id);
    }
}
