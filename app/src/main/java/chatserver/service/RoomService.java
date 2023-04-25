package chatserver.service;

import chatserver.dao.Room;
import chatserver.dao.RoomRepository;
import chatserver.dao.User;
import chatserver.dao.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository rooms;


    public Room addRoom(@NotNull Room room) {
        return rooms.save(room);
    }


    @Cacheable("userByUserId")
    public void updateRoomLastMessageId(long roomId, long messageId) {

    }

    public List<Room> findByUserId(long userId) {
        return rooms.findByUserId(userId);
    }

}
