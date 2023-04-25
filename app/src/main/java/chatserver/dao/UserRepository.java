package chatserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserId(long userId);

    User findByPhone(String phone);

    User findByEmail(String email);

}
