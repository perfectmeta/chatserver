package chatserver.logic;

import chatserver.entity.User;
import chatserver.gen.Contact;
import chatserver.gen.UpdateSelfInfoResponse;
import chatserver.logic.util.UserUtils;
import chatserver.service.UserService;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateSelfInfo {
    private final UserService userService;

    public UpdateSelfInfo(UserService userService) {
        this.userService = userService;
    }

    public void run(Contact roleInfo, StreamObserver<UpdateSelfInfoResponse> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        Objects.requireNonNull(user);

        user.setBirthDay(roleInfo.getBirthDay());
        user.setDisposition(roleInfo.getCharacter());
        user.setEnglishName(roleInfo.getEnglishName());
        user.setFavoriteFoods(roleInfo.getFavoriteFoods());
        user.setInterest(roleInfo.getInterest());
        user.setLifeGoal(roleInfo.getLifeGoal());
        user.setPersonalizedSignature(roleInfo.getPersonalizedSignature());
        user.setLocation(roleInfo.getLocation());
        user.setUnInterest(roleInfo.getUnInterest());

        user = this.userService.addUser(user);
        Context.current().withValue(AuthTokenInterceptor.USER, user);

        var response = UpdateSelfInfoResponse.newBuilder()
                .setResult(0)
                .setContact(UserUtils.parseContactByDbUser(user))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
