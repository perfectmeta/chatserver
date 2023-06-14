package chatserver.logic;

import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.gen.RegisterFeedback;
import chatserver.gen.RegisterInfo;
import chatserver.logic.internal.SignupBot;
import chatserver.service.ContactService;
import chatserver.service.UserService;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Signup {
    private static final Logger logger = Logger.getLogger(Signup.class.getName());
    final UserService userService;
    final ContactService contactService;
    final SignupBot signupBotServer;

    public Signup(UserService userService,
                  ContactService contactService,
                  SignupBot signupBotServer) {
        this.userService = userService;
        this.contactService = contactService;
        this.signupBotServer = signupBotServer;
    }

    @Transactional
    public void run(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        var email = request.getEmail();
        var phone = request.getPhone();
        var nickName = request.getNickname();


        User dbUser = userService.findByNickName(nickName);
        if (dbUser != null) {
            var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                    .setUserId((int)dbUser.getUserId()).build();
            logger.info("signin by nickname: " + nickName + " token: " + dbUser.getUserId());
            responseObserver.onNext(feedback);
            responseObserver.onCompleted();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUserType(EUserType.HUMAN);    // 人类类别为1
        user.setPhone(phone);
        user.setNickName(nickName);
        if ((dbUser=userService.addUser(user)) == null) {
            var res = RegisterFeedback.newBuilder()
                    .setStatusCode(RegisterFeedback.StatusCode.OTHER_ERROR_VALUE)
                    .setMessage("DB error").build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            logger.warning("Signup failed since db error, nick name: " + nickName);
            return;
        }
        var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                        .setUserId((int)dbUser.getUserId()).build();
        signupBotAndMakeContact(dbUser.getUserId());
        responseObserver.onNext(feedback);
        responseObserver.onCompleted();
        logger.info("Signup success register nickname " + nickName + " token " + dbUser.getUserId());
    }

    private void signupBotAndMakeContact(long userId) {
        User botUser = signupBotServer.signupFor(2);
        contactService.addContact(botUser.getUserId(), userId);
        contactService.addContact(userId, botUser.getUserId());
    }
}
