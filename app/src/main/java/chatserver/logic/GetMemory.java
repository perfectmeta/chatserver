package chatserver.logic;

import chatserver.gen.GetMemoryRequest;
import chatserver.gen.Memory;
import chatserver.gen.MemoryList;
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

    public void run(GetMemoryRequest request, StreamObserver<MemoryList> responseObserver) {
        var userId = request.getUserId();
        var otherUserId = request.getOtherUserId();

        var memories = contactService.getAllMemory(userId, otherUserId);
        var memoryListBuilder = MemoryList.newBuilder();
        memories.stream().map(GetMemory::parseMemoryFromDb).forEach(memoryListBuilder::addMemoryList);
        responseObserver.onNext(memoryListBuilder.build());
        responseObserver.onCompleted();
        logger.info("get memory finised, size " + memories.size());
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
