package chatserver.service;

import chatserver.dao.MemoryRepository;
import chatserver.dao.UserRepository;
import chatserver.entity.Contact;
import chatserver.dao.ContactRepository;
import chatserver.entity.Memory;
import chatserver.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final MemoryRepository memoryRepository;

    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository,
                          MemoryRepository memoryRepository,
                          UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.memoryRepository = memoryRepository;
        this.userRepository = userRepository;
    }

    public List<Contact> getAllContactsByUserId(long userId) {
        return contactRepository.findAllByUserId(userId);
    }

    public List<Contact> getContacts(long userId, long targetId) {
        var user = new User();
        user.setUserId(userId);
        var target = new User();
        target.setUserId(targetId);
        return contactRepository.findAllByUserIdAndContactUserId(user, target);
    }

    public List<Memory> getAllMemory(long userId, long otherId) {
        return memoryRepository.findAllByUserIdAndOtherUserId(userId, otherId);
    }

    public Memory getNewestMemory(long userId, long otherId) {
        return memoryRepository.findTopByUserIdAndOtherUserIdOrderByCreatedTimeDesc(userId, otherId);
    }

    @Transactional
    public int deleteMemoryByUserIdAndOtherUserIdAndMemoryId(long userId, long otherId, long memoryId) {
        return memoryRepository.deleteByUserIdAndOtherUserIdAndMemoryId(userId, otherId, memoryId);
    }

    @SuppressWarnings("UnusedReturnValue")
    public List<Long> makeContactForUser(long userId) {
        List<User> botUsers = userRepository.findAllByUserCategoryNotIn(List.of(1));
        List<Contact> contacts = new ArrayList<>();
        for (User user : botUsers) {
            Contact contact = new Contact();
            User u = new User();
            u.setUserId(userId);
            User o = new User();
            o.setUserId(user.getUserId());
            contact.setUserId(u);
            contact.setContactUserId(o);
            contacts.add(contact);
        }
        contactRepository.saveAll(contacts);
        return contacts.stream().map(x->x.getContactUserId().getUserId()).collect(Collectors.toList());
    }

    public Contact addContact(long userId, long targetUserId) {
        User user = new User();
        user.setUserId(userId);
        User target = new User();
        user.setUserId(targetUserId);
        Contact contact = new Contact();
        contact.setUserId(user);
        contact.setContactUserId(target);
        return contactRepository.save(contact);
    }

    @SuppressWarnings("unused")
    public void deleteContact(User user, User target) {
        Contact contact = new Contact();
        contact.setUserId(user);
        contact.setContactUserId(target);
        deleteContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
