package chatserver.logic;

import chatserver.entity.User;
import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetContactStream {

    private final ContactService contactService;

    public GetContactStream(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(Hello request, StreamObserver<Contact> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<chatserver.entity.Contact> contacts = contactService.getAllContactsByUserId(user.getUserId());
        for (var c : contacts) {
            responseObserver.onNext(makeContact(c));
        }

        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerContactStreamObserver(responseObserver);
    }

    private static Contact makeContact(chatserver.entity.Contact contact) {
        var builder = Contact.newBuilder();
        builder.setCategoryName(contact.getContactUserId().getNickName());
        builder.setNickName(contact.getContactUserId().getPhone());
        return builder.build();
    }
}
