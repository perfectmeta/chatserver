package prompteditor.service;

import org.springframework.stereotype.Service;
import prompteditor.dao.UserCategoryRepository;
import prompteditor.dto.UserCategoryPOJO;
import prompteditor.entity.UserCategory;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    public UserCategoryPOJO getUserCategories() {
        var dbUserCategories = userCategoryRepository.findByUserCategoryId(2L);
        return parsedUserCategory(dbUserCategories);
    }

    private UserCategoryPOJO parsedUserCategory(UserCategory dbUserCategory) {
        var pojo = new UserCategoryPOJO();
        pojo.setUserCategoryId(dbUserCategory.getUserCategoryId());
        pojo.setDescription(dbUserCategory.getDescription());
        pojo.setUserType(dbUserCategory.getUserType());
        pojo.setGender(dbUserCategory.getGender());
        pojo.setTags(dbUserCategory.getTags());
        pojo.setPrompt(dbUserCategory.getPrompt());
        pojo.setUserCategoryName(dbUserCategory.getUserCategoryName());
        return pojo;
    }

    private List<UserCategoryPOJO> parsedUserCategories(List<UserCategory> dbUserCategories) {
        List<UserCategoryPOJO> userCategories = new ArrayList<>();
        for (var u : dbUserCategories) {
            userCategories.add(parsedUserCategory(u));
        }
        return userCategories;
    }

    public void updateUserCategories(long categoryId, String message) {
        var userCategory = userCategoryRepository.findByUserCategoryId(categoryId);
        userCategory.setPrompt(message);
        userCategoryRepository.save(userCategory);
    }
}
