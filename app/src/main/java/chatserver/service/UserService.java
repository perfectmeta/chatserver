package chatserver.service;

import chatserver.dao.User;
import chatserver.dao.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserService {  // 不抽象成接口，直接用，因为这里不太可能会有扩展

    @Autowired
    private UserRepository users;

    @Caching(evict = {
            @CacheEvict(value = "userByPhone", key = "#p0.phone"),
            @CacheEvict(value = "userByEmail", key = "#p0.email"),
            @CacheEvict(value = "userByUserId", key = "#p0.userId"),
    })
    public User addUser(@NotNull User user){
        return users.save(user);
    }


    @Cacheable("userByUserId")
    public User findByUserId(long userId){
        return users.findByUserId(userId);
    }

    @Cacheable("userByPhone")
    public User findByPhone(String phone) {
        return users.findByPhone(phone);
    }

    @Cacheable("userByEmail")
    public User findByEmail(String email){
        return users.findByEmail(email);
    }

    public void deleteByUserId(long userId) { users.deleteById(userId);}

    public User deleteByPhone(String phone) { return users.deleteByPhone(phone);}
}
