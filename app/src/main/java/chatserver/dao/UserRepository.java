package chatserver.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(long userId);

    User findByPhone(String phone);

    User findByEmail(String email);

    @Transactional
    User deleteByPhone(String phone);
}
