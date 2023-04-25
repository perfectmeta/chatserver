package chatserver.logic;

import chatserver.dao.Room;
import chatserver.gen.Author;
import chatserver.gen.Hello;
import chatserver.gen.RoomInfo;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRoomList {

    @Autowired
    private RoomService roomService;

    public void run(Hello request, StreamObserver<RoomInfo> responseObserver) {

        List<Room> byUserId = roomService.findByUserId(AuthTokenInterceptor.USER.get().getUserId());
        for (Room room : byUserId) {
            RoomInfo.Builder b = RoomInfo.newBuilder();
            b.setRoomId(room.getRoomId());
            b.setRoomName(room.getRoomName());
            b.setYou(Author.newBuilder().setType(0).setUserId(room.getUserId()).setName(room.getUserShowName()).build());
            b.setAi(Author.newBuilder().setType(1).setUserId(room.getAiUserId()).setName(room.getAiShowName()).build());
            b.setCreatedTime(room.getCreatedTime());
            b.setFirstMessageId(room.getFirstMessageId());
            b.setLastMessageId(room.getLastMessageId());
            responseObserver.onNext(b.build());
        }

        responseObserver.onCompleted();
    }
}
