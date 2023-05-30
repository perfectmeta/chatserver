package chatserver.logic;

import chatserver.entity.User;
import chatserver.gen.EstablishContactWithRequest;
import chatserver.gen.EstablishContactWithResponse;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EstablishContactWith {
    private final ContactService contactService;

    public EstablishContactWith(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(EstablishContactWithRequest request, StreamObserver<EstablishContactWithResponse> responseStream) {
        User user = AuthTokenInterceptor.USER.get();
        var targetUserId = request.getUserId();
        var contacts = contactService.getContacts(user.getUserId(), targetUserId);
        if (contacts != null && contacts.size() > 0) {
            responseStream.onError(new IllegalStateException("Have contacts with the id " + targetUserId + " already"));
        }
        var contact = contactService.addContact(user.getUserId(), targetUserId);
        if (contact == null) {
            responseStream.onError(new IllegalStateException("Permission error"));
        }
        var response = EstablishContactWithResponse.newBuilder().
                setContactId(Objects.requireNonNull(contact).getContactId())
                .build();
        responseStream.onNext(response);
        responseStream.onCompleted();
    }
}
