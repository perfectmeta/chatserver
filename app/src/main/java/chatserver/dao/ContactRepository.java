package chatserver.dao;

import chatserver.entity.Contact;
import chatserver.entity.ContactKey;
import chatserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, ContactKey> {
    Contact findBySubjectUserIdAndObjectUserId(long subjectUserId, long objectUserId);

    List<Contact> findBySubjectUserId(long subjectUserId);

}