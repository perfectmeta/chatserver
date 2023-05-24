package chatserver.logic;

import chatserver.entity.User;
import chatserver.gen.RegisterFeedback;
import chatserver.gen.RegisterInfo;
import chatserver.service.ContactService;
import chatserver.service.UserService;
import chatserver.util.Validator;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class Signup {
    final UserService userService;
    final ContactService contactService;

    public Signup(UserService userService,
                  ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    public void run(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        var email = request.getEmail();
        var phone = request.getPhone();
        if (!Validator.checkEmailAddressRule(email)) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.EMAIL_INVALID_VALUE, "Invalid email address"));
            responseObserver.onCompleted();
            return;
        }

        if (!Validator.checkPhoneNumberRule(phone)) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.PHONE_INVALID_VALUE, "Invalid phone number"));
            responseObserver.onCompleted();
            return;
        }

        if (userService.findByEmail(email) != null) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.EMAIL_CONFLICT_VALUE, "email registered"));
            responseObserver.onCompleted();
            return;
        }

        if (userService.findByPhone(phone) != null) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.EMAIL_CONFLICT_VALUE, "phone registered"));
            responseObserver.onCompleted();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUserCategory(0);
        user.setPhone(phone);
        user.setNickName(request.getNickname());
        User dbUser;
        if ((dbUser=userService.addUser(user)) == null) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.OTHER_ERROR_VALUE, "DB error"));
            responseObserver.onCompleted();
            return;
        }
        makeAllBotAsContact(dbUser.getUserId());
        var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                        .setUserId((int)dbUser.getUserId()).build();
        responseObserver.onNext(feedback);
        responseObserver.onCompleted();
    }

    private void makeAllBotAsContact(long userId) {
        contactService.makeContactForUser(userId);
    }

    private RegisterFeedback error(int code, String message) {
        return RegisterFeedback.newBuilder().setStatusCode(code).setMessage(message).build();
    }
}
