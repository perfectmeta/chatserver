package chatserver.logic;

import chatserver.entity.EUserType;
import chatserver.entity.User;
import chatserver.gen.CreateMineNPCsRequest;
import chatserver.gen.CreateMineNPCsResponse;
import chatserver.service.UserService;
import chatserver.util.RandomGenerator;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class CreateMineNPCs {
    private final UserCategoryService userCategoryService;
    private final UserService userService;

    public CreateMineNPCs(UserCategoryService userCategoryService, UserService userService) {
        this.userCategoryService = userCategoryService;
        this.userService = userService;
    }

    public void run(CreateMineNPCsRequest request, StreamObserver<CreateMineNPCsResponse> responseStreamObserver) {
        var  category = userCategoryService.findUserCategoryById(request.getCategory());
        if (category == null) {
            responseStreamObserver.onError(new IllegalArgumentException("Invalid categoryId:" + request.getCategory()));
            return;
        }

        User newUser = new User();
        newUser.setPhone(RandomGenerator.randomPhoneNumber());
        newUser.setEmail(RandomGenerator.randomEmailAddress());
        newUser.setUserType(EUserType.BOT);
        newUser.setBotId("aya");
        newUser.setGender(category.getGender());
        newUser.setNickName(category.getUserCategoryName());
        newUser = userService.addUser(newUser);
        if (newUser == null) {
            responseStreamObserver.onError(new IllegalStateException("Persistent procedure error"));
            return;
        }

        var response = CreateMineNPCsResponse.newBuilder().setUserId(newUser.getUserId()).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
