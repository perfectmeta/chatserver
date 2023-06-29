package chatserver.logic;


import chatserver.gen.Contact;
import chatserver.gen.Hello;
import chatserver.logic.util.UserUtils;
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
        responseObserver.onNext(UserUtils.parseContactByDbUser(dbuser));
        responseObserver.onCompleted();
    }
}
