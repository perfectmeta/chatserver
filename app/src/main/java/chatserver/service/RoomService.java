package chatserver.service;

import chatserver.dao.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository rooms;

    private final AICharacterRepository aiCharacter;

    private final MessageRepository messages;

    @Autowired
    public RoomService(RoomRepository rooms, AICharacterRepository aiCharacter, MessageRepository messages) {
        this.rooms = rooms;
        this.aiCharacter = aiCharacter;
        this.messages = messages;
    }


    public List<Room> findByUserId(long userId) {
        return rooms.findByUserId(userId);
    }

    public Room upsertRoom(@NotNull Room room) {
        return rooms.save(room);
    }

    public Room findRoomById(long roomId) {
        return rooms.findByRoomId(roomId);
    }

    public Message addMessage(@NotNull Message message) {  // message除了没messageId信息，其他都填好了
        Room room = rooms.findByRoomId(message.getRoomId());
        if (room == null) {
            return null;
        }
        Message newMsg = messages.save(message);
        room.setLastMessageId(newMsg.getMessageId());  // TODO： 把事务考虑清楚
        rooms.save(room);

        return newMsg; // 返回newMsg，里面有messageId
    }

    public List<Message> getMessageHistorySince(long roomId, long fromMessageId) {
        return messages.findByRoomIdAndMessageIdGreaterThanOrderByMessageIdAsc(roomId, fromMessageId);
    }

    public Long getRoomMaxMessageId(long roomId) {
        return messages.findMaxMessageInRoomId(roomId);
    }

    public List<Message> getMessageHistory(long roomId) {
        return messages.findFirst100ByRoomIdOrderByMessageIdDesc(roomId);
    }

    public AICharacter getAICharacterInRoom(long roomId) {
        return aiCharacter.findAICharacterByRoom_RoomId(roomId);
    }
}
