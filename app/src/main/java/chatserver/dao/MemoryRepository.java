package chatserver.dao;

import chatserver.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
    List<Memory> findAllByUserIdAndOtherUserId(long userId, long otherUserId);
    //List<Memory> findTopByUserIdOrderByCreatedTimeDesc(long userId);

    Memory findTopByUserIdAndOtherUserIdOrderByCreatedTimeDesc(long userId, long otherUserId);
    int deleteByUserIdAndOtherUserIdAndMemoryId(long userId, long otherUserId, long memoryId);
}