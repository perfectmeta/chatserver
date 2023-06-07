package chatserver.logic;

import chatserver.entity.Message;
import chatserver.entity.Room;
import chatserver.entity.User;
import chatserver.entity.UserCategory;
import chatserver.gen.Author;
import chatserver.gen.Hello;
import chatserver.gen.RoomInfo;
import chatserver.service.UserCategoryService;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class GetRoomStream {
    private static final Logger logger = Logger.getLogger(GetRoomStream.class.getName());

    private final RoomService roomService;
    private final UserCategoryService userCategoryService;

    @Autowired
    public GetRoomStream(RoomService roomService, UserCategoryService userCategoryService) {
        this.roomService = roomService;
        this.userCategoryService = userCategoryService;
    }

    private boolean containRoom(long aiCharacter, Collection<Room> rooms) {
        return rooms.stream().mapToLong(Room::getAiUserId).anyMatch(r->r==aiCharacter);
    }

    public void run(Hello ignoredRequest, StreamObserver<RoomInfo> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<Room> userRooms = roomService.findByUserId(user.getUserId());
        logger.info("User " + user.getUserId() + " Searched Rooms " + userRooms.size());
        fillRooms(userRooms, user);
        for (Room room : userRooms) {
            responseObserver.onNext(parseRoomInfo(room));
        }
        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerRoomInfoStreamObserver(responseObserver);
    }

    private void fillRooms(List<Room> userRooms, User user) {
        List<UserCategory> userCategories = userCategoryService.findAllBotUserCategories();
        for (var character : userCategories) {
            if (containRoom(character.getUserCategoryId(), userRooms)) {
                continue;
            }
            var room = makeRoom(character, user);
            if (room != null) {
                userRooms.add(room);
                fillFirstMessage(room.getRoomId(), character.getUserCategoryName(), user.getNickName());
            }
        }
    }

    private Room makeRoom(UserCategory userCategory, User user) {
        Room newRoom = new Room();
        newRoom.setRoomName(userCategory.getUserCategoryName() + "'s room");
        newRoom.setUserType(Msg.UT_HUMAN);
        newRoom.setUserId(user.getUserId());
        newRoom.setUserShowName(user.getNickName());
        newRoom.setAiType(Msg.UT_AI_TEACHER);
        newRoom.setAiUserId(userCategory.getUserCategoryId());
        newRoom.setAiShowName(userCategory.getUserCategoryName());
        newRoom.setCreatedTime(System.currentTimeMillis());
        newRoom.setFirstMessageId(-1);
        newRoom.setLastMessageId(-1);
        return roomService.upsertRoom(newRoom);
    }

    private void fillFirstMessage(long roomId, String userCategoryName, String nickName) {
        Message message = new Message();
        message.setRoomId(roomId);
        message.setCreatedTime(System.currentTimeMillis());
        message.setText("你好呀%s, 我是%s，很高兴认识你!".formatted(nickName, userCategoryName));
        roomService.addMessage(message);
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
