package chatserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findFirst50ByRoomIdOrderByMessageIdDesc(long roomId);

    List<Message> findFirst50ByRoomIdAndMessageIdLessThanOrderByMessageIdDesc(long roomId, long messageId);

    List<Message> findByRoomIdAndMessageIdGreaterThanOrderByMessageIdAsc(long roomId, long messageId);

}
