package chatserver.service;

import chatserver.dao.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messages;

    public MessageService(MessageRepository messages) {
        this.messages = messages;
    }

    @Transactional
    public long deleteMessageByRoomId(Long roomId) {
        return messages.deleteByRoomId(roomId);
    }
}
