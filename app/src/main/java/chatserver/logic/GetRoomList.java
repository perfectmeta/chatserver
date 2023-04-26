package chatserver.logic;

import chatserver.dao.Room;
import chatserver.dao.User;
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

        User user = AuthTokenInterceptor.USER.get();
        List<Room> byUserId = roomService.findByUserId(user.getUserId());

        if (byUserId.isEmpty()) {
            Room newRoom = new Room();
            newRoom.setRoomName("english classroom");
            newRoom.setUserType(Msg.UT_HUMAN);
            newRoom.setUserId(user.getUserId());
            newRoom.setUserShowName(user.getNickName());
            newRoom.setAiType(Msg.UT_AI_TEACHER);
            newRoom.setAiUserId(-1);  // TODO 给它id
            newRoom.setAiShowName("lisa");
            newRoom.setCreatedTime(System.currentTimeMillis());
            newRoom.setFirstMessageId(-1);
            newRoom.setLastMessageId(-1);
            roomService.addRoom(newRoom);

            byUserId = roomService.findByUserId(user.getUserId());
        }


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
