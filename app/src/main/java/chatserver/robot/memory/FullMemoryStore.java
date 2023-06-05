package chatserver.robot.memory;

import chatserver.robot.message.IMessage;
import chatserver.service.ContactService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullMemoryStore implements IConversationMemoryStore {
    private List<IMessage> messages = new ArrayList<>();
    private final long aiUserId;
    private final long humanUserId;
    private final ContactService contactService;

    public FullMemoryStore(long aiUserId, long humanUserId, ContactService contactService) {
        this.aiUserId = aiUserId;
        this.humanUserId = humanUserId;
        this.contactService = contactService;
    }

    @Override
    public List<IMessage> memories() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public void initMemory() {
        initFromDB();
    }

    private void initFromDB() {

    }
}
