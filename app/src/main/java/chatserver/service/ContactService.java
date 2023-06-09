package chatserver.service;

import chatserver.dao.ContactRepository;
import chatserver.dao.MemoryRepository;
import chatserver.entity.Contact;
import chatserver.entity.Memory;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final MemoryRepository memoryRepository;

    public ContactService(ContactRepository contactRepository,
                          MemoryRepository memoryRepository) {
        this.contactRepository = contactRepository;
        this.memoryRepository = memoryRepository;
    }

    public List<Contact> findBySubjectUserId(long userId) {
        return contactRepository.findBySubjectUserId(userId);
    }

    public Contact findBySubjectUserIdAndObjectUserId(long subjectUserId, long objectUserId) {
        return contactRepository.findBySubjectUserIdAndObjectUserId(subjectUserId, objectUserId);
    }

    public @NotNull Contact addContact(long subjectUserId, long objectUserId) {
        Contact contact = new Contact();
        contact.setSubjectUserId(subjectUserId);
        contact.setObjectUserId(objectUserId);
        contact.setCreatedTime(System.currentTimeMillis());
        return contactRepository.save(contact);
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

    public void makeContactForUser(long userId) {
        addContact(userId, 1L); // FIXME 假设1是bot，第一个加入
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
