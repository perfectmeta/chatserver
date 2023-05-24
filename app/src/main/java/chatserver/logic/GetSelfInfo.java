package chatserver.logic;


import chatserver.entity.User;
import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.service.UserService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetSelfInfo {
    // private static final Logger logger = Logger.getLogger(GetSelfInfo.class.getName());
    private final UserService userService;

    public GetSelfInfo(UserService userService) {
        this.userService = userService;
    }

    public void run(Hello request, StreamObserver<Contact> responseObserver) {
        Objects.requireNonNull(request);
        var user = AuthTokenInterceptor.USER.get();
        var dbuser = userService.findByUserId(user.getUserId());
        responseObserver.onNext(parseContactByDbUser(dbuser));
        responseObserver.onCompleted();
    }

    private static Contact parseContactByDbUser(User user) {
        var builder = Contact.newBuilder();
        builder.setNickName(user.getNickName());
        builder.setGender(user.getGender());
        builder.setHeadIconUrl(user.getHeadIconUrl());
        return builder.build();
    }
}
