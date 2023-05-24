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

    public List<Memory> getAllMemory(long userId, long otherId) {
        return memoryRepository.findAllByUserIdAndOtherUserId(userId, otherId);
    }

    @Transactional
    public int deleteMemoryByUserIdAndOtherUserIdAndMemoryId(long userId, long otherId, long memoryId) {
        return memoryRepository.deleteByUserIdAndOtherUserIdAndMemoryId(userId, otherId, memoryId);
    }

    public void makeContactForUser(long userId) {
        List<User> botUsers = userRepository.findAllByUserCategoryNotIn(List.of(0));
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
    }
}
