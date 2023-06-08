package chatserver.logic;

import chatserver.entity.Contact;
import chatserver.entity.User;
import chatserver.gen.DeleteContactRequest;
import chatserver.gen.DeleteContactResponse;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteContact {
    private final ContactService contactService;

    public DeleteContact(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(DeleteContactRequest request, StreamObserver<DeleteContactResponse> responseStream) {
        User user = AuthTokenInterceptor.USER.get();
        User target = new User();
        target.setUserId(request.getUserId());
        Contact contact = contactService.findBySubjectUserIdAndObjectUserId(user.getUserId(), request.getUserId());
        if (contact == null) {
            var response = DeleteContactResponse.newBuilder().setErrCode(-1).build();
            responseStream.onNext(response);
        } else {
            contactService.deleteContact(contact);
            var response = DeleteContactResponse.newBuilder().setErrCode(0).build();
            responseStream.onNext(response);
            responseStream.onCompleted();
        }
    }
}
