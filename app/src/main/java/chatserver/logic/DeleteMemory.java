package chatserver.logic;

import chatserver.entity.User;
import chatserver.gen.DeleteMemoryRequest;
import chatserver.gen.DeleteMemoryResponse;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DeleteMemory {
    private static final Logger logger = Logger.getLogger(DeleteMemory.class.getName());
    private final ContactService contactService;

    public DeleteMemory(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(DeleteMemoryRequest request, StreamObserver<DeleteMemoryResponse> responseStreamObserver) {
        User user = AuthTokenInterceptor.USER.get();
        var userId = request.getUserId();
        var errCode = 0;
        if (user == null || user.getUserId() != userId) {
            errCode = -1;
        } else {
            var otherUserId = request.getOtherUserId();
            var memoryId = request.getMemoryId();
            var deleteCnt = contactService.deleteMemoryByUserIdAndOtherUserIdAndMemoryId(userId, otherUserId, memoryId);
            if (deleteCnt != 1) {
                errCode = -1;
            }
        }
        // todo 丰富更多的错误内容，错误原因，现在先只简单报告错误
        var response = DeleteMemoryResponse.newBuilder().setErrorCode(errCode).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
