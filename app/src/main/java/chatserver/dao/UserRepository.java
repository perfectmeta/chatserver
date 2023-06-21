package chatserver.dao;

import chatserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(long userId);
    User findByPhone(String phone);
    User findByEmail(String email);
    int deleteByPhone(String phone);
    User findByNickName(String nickName);
    List<User> findByUserIdIn(List<Long> userIds);
}
