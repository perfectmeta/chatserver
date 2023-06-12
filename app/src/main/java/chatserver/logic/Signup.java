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

@Component
public class Signup {
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
            responseObserver.onNext(feedback);
            responseObserver.onCompleted();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUserType(EUserType.HUMAN);    // 人类类别为1
        user.setPhone(phone);
        user.setNickName(request.getNickname());
        if ((dbUser=userService.addUser(user)) == null) {
            var res = RegisterFeedback.newBuilder()
                    .setStatusCode(RegisterFeedback.StatusCode.OTHER_ERROR_VALUE)
                    .setMessage("DB error").build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }
        var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                        .setUserId((int)dbUser.getUserId()).build();
        responseObserver.onNext(feedback);
        responseObserver.onCompleted();
        signupBotAndMakeContact(dbUser.getUserId());
    }

    private void signupBotAndMakeContact(long userId) {
        User botUser = signupBotServer.signupFor(2);
        contactService.addContact(botUser.getUserId(), userId);
        contactService.addContact(userId, botUser.getUserId());
    }
}
