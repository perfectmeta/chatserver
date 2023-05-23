package chatserver.logic;

import chatserver.dao.User;
import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetContactList {

    private final ContactService contactService;

    public GetContactList(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(Hello request, StreamObserver<Contact> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<chatserver.dao.Contact> contacts = contactService.getAllContactsByUserId(user.getUserId());
        for (var c : contacts) {
            responseObserver.onNext(makeContact(c));
        }
    }

    private static Contact makeContact(chatserver.dao.Contact contact) {
        var builder = Contact.newBuilder();
        // builder.setCategoryName(contact.get);
        return builder.build();
    }
}
