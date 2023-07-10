package chatserver.logic;

import chatserver.config.ConfigManager;
import chatserver.config.RobotConfig;
import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.service.ContactService;
import chatserver.service.UserService;
import com.google.common.base.Strings;
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

        builder.setNickName(object.getNickName());
        if (object.getUserType() == EUserType.BOT) {
            builder.setCategoryName(object.getBotId());
            RobotConfig robotConfig = ConfigManager.getInstance().getRobotByName(object.getBotId());
            if (robotConfig != null) {
                builder.setArtistModel(robotConfig.getArtistModel());
            }
        } else {
            builder.setBirthDay(Strings.nullToEmpty(object.getBirthDay()));
            builder.setEnglishName(Strings.nullToEmpty(object.getEnglishName()));
            builder.setLocation(Strings.nullToEmpty(object.getLocation()));
            builder.setInterest(Strings.nullToEmpty(object.getInterest()));
        }
        return builder.build();
    }
}
