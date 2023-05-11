package chatserver.service;

import chatserver.dao.Message;
import chatserver.dao.MessageRepository;
import chatserver.dao.Room;
import chatserver.dao.RoomRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository rooms;

    @Autowired
    private MessageRepository messages;


    public List<Room> findByUserId(long userId) {
        return rooms.findByUserId(userId);
    }

    public Room addRoom(@NotNull Room room) {
        return rooms.save(room);
    }

    public Room findRoomById(long roomId) { return rooms.findByRoomId(roomId); }

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

    public List<Message> getMessageHistory(long roomId) {
        return messages.findFirst100ByRoomIdOrderByMessageIdDesc(roomId);
    }


}
