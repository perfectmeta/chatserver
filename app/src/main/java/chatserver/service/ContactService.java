package chatserver.service;

import chatserver.dao.MemoryRepository;
import chatserver.entity.Contact;
import chatserver.dao.ContactRepository;
import chatserver.entity.Memory;
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

    public List<Contact> getAllContactsByUserId(long userId) {
        return contactRepository.findAllByUserId(userId);
    }

    public List<Memory> getAllMemory(long userId, long otherId) {
        return memoryRepository.findAllByUserIdAndOtherUserId(userId, otherId);
    }
}
