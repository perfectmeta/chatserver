package chatserver.logic;

import chatserver.dao.User;
import chatserver.gen.RegisterFeedback;
import chatserver.gen.RegisterInfo;
import chatserver.service.UserService;
import chatserver.util.Validator;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Signup {
    @Autowired
    UserService userService;

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
        user.setUserType(0);
        user.setPhone(phone);
        user.setNickName(request.getNickname());
        User dbUser;
        if ((dbUser =userService.addUser(user)) == null) {
            responseObserver.onNext(error(RegisterFeedback.StatusCode.OTHER_ERROR_VALUE, "DB error"));
            responseObserver.onCompleted();
            return;
        }
        var feedbackback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                        .setUserId(dbUser.getUserId().intValue()).build();
        responseObserver.onNext(feedbackback);
        responseObserver.onCompleted();
    }

    private RegisterFeedback error(int code, String message) {
        return RegisterFeedback.newBuilder().setStatusCode(code).setMessage(message).build();
    }

}
