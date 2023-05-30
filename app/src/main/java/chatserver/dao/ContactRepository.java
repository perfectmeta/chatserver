package chatserver.dao;

import chatserver.entity.Contact;
import chatserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c where c.userId.userId = ?1")
    List<Contact> findAllByUserId(long userId);

    List<Contact> findAllByUserIdAndContactUserId(User userId, User contactUserId);
}