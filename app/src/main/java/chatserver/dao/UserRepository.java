package chatserver.dao;

import chatserver.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(long userId);

    User findByPhone(String phone);

    User findByEmail(String email);

    @Transactional
    int deleteByPhone(String phone);

    List<User> findAllByUserCategoryNotIn(List<Integer> userCategory);
}
