package chatserver.robot.memory;

import chatserver.robot.message.IMessage;

import java.util.List;

public interface IConversationMemoryStore {
    List<IMessage> memories();
    void initMemory();
}
