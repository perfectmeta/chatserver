package chatserver.dao;

import chatserver.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    UserCategory findByUserCategoryId(long userCategoryId);

    List<UserCategory> findAllByUserTypeIsNot(int userType);
}
