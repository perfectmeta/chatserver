package chatserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findFirst100ByRoomIdOrderByMessageIdDesc(long roomId);

    List<Message> findFirst50ByRoomIdAndMessageIdLessThanOrderByMessageIdDesc(long roomId, long toMessageId);

    List<Message> findByRoomIdAndMessageIdGreaterThanOrderByMessageIdAsc(long roomId, long fromMessageId);

}
