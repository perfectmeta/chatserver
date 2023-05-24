package chatserver.logic;

import chatserver.gen.GetMemoryRequest;
import chatserver.gen.Memory;
import chatserver.service.ContactService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class GetMemory {
    private final ContactService contactService;

    public GetMemory(ContactService contactService) {
        this.contactService = contactService;
    }

    public void run(GetMemoryRequest request, StreamObserver<Memory> responseObserver) {
        var userId = request.getUserId();
        var otherUserId = request.getOtherUserId();

        var memories = contactService.getAllMemory(userId, otherUserId);
    }

    public static Memory parseMemoryFromDb(chatserver.entity.Memory dbMemory) {
        var builder = Memory.newBuilder();
        builder.setMemoryId(dbMemory.getMemoryId());
        builder.setCreatedTime(dbMemory.getCreatedTime());
        builder.setMemo(dbMemory.getMemo());
        return builder.build();
    }
}
