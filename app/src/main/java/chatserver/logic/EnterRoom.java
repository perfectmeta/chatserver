package chatserver.logic;

import chatserver.gen.EnterRoomRequest;
import chatserver.gen.Message;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnterRoom {
    @Autowired
    private RoomService roomService;

    public void run(EnterRoomRequest request, StreamObserver<Message> responseObserver) {
        List<chatserver.dao.Message> messageHistory =
                roomService.getMessageHistorySince(request.getRoomId(), request.getLastMessageId());

        for (chatserver.dao.Message message : messageHistory) {
            Message msg = Msg.fromDb(message);
            responseObserver.onNext(msg);
        }
        responseObserver.onCompleted();
    }
}
