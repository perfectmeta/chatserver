package chatserver.service;

import chatserver.dao.Contact;
import chatserver.dao.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContactsByUserId(long userId) {
        return contactRepository.findAllByUserId(userId);
    }
}
