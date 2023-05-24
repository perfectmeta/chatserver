package chatserver.service;

import chatserver.entity.UserCategory;
import chatserver.dao.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    public List<UserCategory> findAllUserCategories() {
        return userCategoryRepository.findAll();
    }

    public List<UserCategory> findAllBotUserCategories() {
        return userCategoryRepository.findAllByUserTypeIsNot(0);
    }

    public UserCategory findUserCategoryById(long id) {
        return userCategoryRepository.findByUserCategoryId(id);
    }

    public void addUserCategory(UserCategory uc) {
        userCategoryRepository.save(uc);
    }
}
