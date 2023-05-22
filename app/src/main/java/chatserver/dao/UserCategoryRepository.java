package chatserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    UserCategory findByUserCategoryId(long userCategoryId);
}
