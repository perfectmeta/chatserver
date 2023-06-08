package chatserver.logic;

import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.service.ContactService;
import chatserver.service.UserService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetContactStream {

    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public GetContactStream(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    public void run(Hello request, StreamObserver<Contact> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<chatserver.entity.Contact> contacts = contactService.findBySubjectUserId(user.getUserId());
        for (var c : contacts) {
            responseObserver.onNext(toMsg(c.getObjectUserId()));
        }

        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerContactStreamObserver(responseObserver);
    }

    private Contact toMsg(long objectUserId) {
        User object = userService.findByUserId(objectUserId);
        var builder = Contact.newBuilder();

        // TODO 属性没设置全
        if (object.getUserType() == EUserType.BOT) {
            builder.setCategoryName(object.getBotId());
        }
        builder.setNickName(object.getNickName());
        return builder.build();
    }
}
