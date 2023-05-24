package chatserver.logic;

import chatserver.gen.GetMemoryRequest;
import chatserver.gen.Memory;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class GetMemory {
    private static final Logger logger = Logger.getLogger(GetMemory.class.getName());
    private final ContactService contactService;

    public GetMemory(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(GetMemoryRequest request, StreamObserver<Memory> responseObserver) {
        var userId = request.getUserId();
        var otherUserId = request.getOtherUserId();

        var memories = contactService.getAllMemory(userId, otherUserId);
        for (var m : memories) {
            responseObserver.onNext(parseMemoryFromDb(m));
        }
        responseObserver.onCompleted();
        logger.info("get memory finised");
    }

    public static Memory parseMemoryFromDb(chatserver.entity.Memory dbMemory) {
        var builder = Memory.newBuilder();
        builder.setMemoryId(dbMemory.getMemoryId());
        builder.setCreatedTime(dbMemory.getCreatedTime());
        builder.setMemo(dbMemory.getMemo());
        builder.setUserId(dbMemory.getUserId());
        builder.setOtherUserId(dbMemory.getOtherUserId());
        return builder.build();
    }
}
