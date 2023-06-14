package chatserver.logic;

import chatserver.entity.Room;
import chatserver.entity.User;
import chatserver.gen.EnterRoomRequest;
import chatserver.gen.MessageList;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class EnterRoom {

    private final Logger logger = Logger.getLogger(EnterRoom.class.getName());
    private final RoomService roomService;

    @Autowired
    public EnterRoom(RoomService roomService) {
        this.roomService = roomService;
    }

    public void run(EnterRoomRequest request, StreamObserver<MessageList> responseObserver) {
        Room room = roomService.findRoomById(request.getRoomId());
        User user = AuthTokenInterceptor.USER.get();

        if (room == null || room.getUserId() != user.getUserId()) {
            logger.warning("room not found or user not match");
            responseObserver.onCompleted();
            return;
        }

        List<chatserver.entity.Message> messageHistory =
                roomService.getMessageHistorySince(request.getRoomId(), request.getLastMessageId());

        var messageListBuilder = MessageList.newBuilder();
        messageHistory.stream().map(Msg::fromDb).forEach(messageListBuilder::addMessageList);
        responseObserver.onNext(messageListBuilder.build());
        responseObserver.onCompleted();

        // FIXME 进入房间后，最新的消息应该更新到最新了, 当前阶段，先注释掉这个，让每次都能获取完整的消息列表吧
        // room.setLastMessageId(roomService.getRoomMaxMessageId(room.getRoomId()));
        // roomService.upsertRoom(room);
    }
}
