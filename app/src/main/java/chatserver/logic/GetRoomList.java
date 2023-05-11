package chatserver.logic;

import chatserver.dao.AICharacter;
import chatserver.dao.Room;
import chatserver.dao.User;
import chatserver.gen.Author;
import chatserver.gen.Hello;
import chatserver.gen.RoomInfo;
import chatserver.service.CharacterService;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class GetRoomList {

    private final RoomService roomService;
    private final CharacterService characterService;

    @Autowired
    public GetRoomList(RoomService roomService, CharacterService characterService) {
        this.roomService = roomService;
        this.characterService = characterService;
    }

    private boolean containRoom(long aiCharacter, Collection<Room> rooms) {
        return rooms.stream().mapToLong(Room::getAiUserId).anyMatch(r->r==aiCharacter);
    }

    public void run(Hello ignoredRequest, StreamObserver<RoomInfo> responseObserver) {
        User user = AuthTokenInterceptor.USER.get();
        List<Room> userRooms = roomService.findByUserId(user.getUserId());
        List<AICharacter> aiCharacters = characterService.getAllAICharacters();

        for (var character : aiCharacters) {
            if (containRoom(character.getCharacterId(), userRooms)) {
                continue;
            }
            var room = makeRoom(character, user);
            if (room != null) {
                userRooms.add(room);
            }
        }

        for (Room room : userRooms) {
            responseObserver.onNext(parseRoomInfo(room));
        }
        responseObserver.onCompleted();
    }

    private Room makeRoom(AICharacter aiCharacter, User user) {
        Room newRoom = new Room();
        newRoom.setRoomName("english classroom");
        newRoom.setUserType(Msg.UT_HUMAN);
        newRoom.setUserId(user.getUserId());
        newRoom.setUserShowName(user.getNickName());
        newRoom.setAiType(Msg.UT_AI_TEACHER);
        newRoom.setAiUserId(aiCharacter.getCharacterId());
        newRoom.setAiShowName(aiCharacter.getCharacterName());
        newRoom.setCreatedTime(System.currentTimeMillis());
        newRoom.setFirstMessageId(-1);
        newRoom.setLastMessageId(-1);
        return roomService.addRoom(newRoom);
    }

    private static RoomInfo parseRoomInfo(Room room) {
        RoomInfo.Builder b = RoomInfo.newBuilder();
        b.setRoomId(room.getRoomId());
        b.setRoomName(room.getRoomName());
        b.setYou(Author.newBuilder().setType(0).setUserId(room.getUserId()).setName(room.getUserShowName()).build());
        b.setAi(Author.newBuilder().setType(1).setUserId(room.getAiUserId()).setName(room.getAiShowName()).build());
        b.setCreatedTime(room.getCreatedTime());
        b.setFirstMessageId(room.getFirstMessageId());
        b.setLastMessageId(room.getLastMessageId());
        return b.build();
    }
}
