package chatserver.service;

import chatserver.dao.UserRepository;
import chatserver.entity.UserCategory;
import chatserver.dao.UserCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;
    private final UserRepository userRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository, UserRepository userRepository) {
        this.userCategoryRepository = userCategoryRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public UserCategory findUserCategoryByUserId(long userId) {
        var userEnt = userRepository.findByUserId(userId);
        if (userEnt == null) {
            return null;
        }
        //TODO
        return userCategoryRepository.findByUserCategoryId(2);
    }

    public void addUserCategory(UserCategory uc) {
        userCategoryRepository.save(uc);
    }
}
