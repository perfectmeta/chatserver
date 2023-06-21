package chatserver.logic;

import chatserver.entity.*;
import chatserver.gen.Author;
import chatserver.gen.Hello;
import chatserver.gen.RoomInfo;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class GetRoomStream {
    private static final Logger logger = Logger.getLogger(GetRoomStream.class.getName());
    private final RoomService roomService;
    @Autowired
    public GetRoomStream(RoomService roomService) {
        this.roomService = roomService;
    }

    public void run(Hello ignoredRequest, StreamObserver<RoomInfo> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<Room> userRooms = roomService.findByUserId(user.getUserId());
        logger.info("User " + user.getUserId() + " Searched Rooms " + userRooms.size());
        for (Room room : userRooms) {
            responseObserver.onNext(parseRoomInfo(room));
        }
        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerRoomInfoStreamObserver(responseObserver);
    }

    private static RoomInfo parseRoomInfo(Room room) {
        RoomInfo.Builder b = RoomInfo.newBuilder();
        b.setRoomId(room.getRoomId());
        b.setRoomName(room.getRoomName() == null ? "" : room.getAiShowName());
        b.setYou(Author.newBuilder().setType(0).setUserId(room.getUserId()).setName(room.getUserShowName()).build());
        b.setAi(Author.newBuilder().setType(1).setUserId(room.getAiUserId()).setName(room.getAiShowName()).build());
        b.setCreatedTime(room.getCreatedTime());
        b.setFirstMessageId(room.getFirstMessageId());
        b.setLastMessageId(room.getLastMessageId());
        return b.build();
    }
}
