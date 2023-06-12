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
        Contact subjectContact = new Contact();
        subjectContact.setSubjectUserId(subjectUserId);
        subjectContact.setObjectUserId(objectUserId);
        subjectContact.setCreatedTime(System.currentTimeMillis());
        subjectContact = contactRepository.save(subjectContact);
        return subjectContact;
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

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
