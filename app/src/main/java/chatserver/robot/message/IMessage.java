package chatserver.robot.message;

public interface IMessage {
    enum MessageType {
        SYSTEM_MESSAGE,
        AI_MESSAGE,
        USER_MESSAGE;
    }

    MessageType getType();
    String getContent();
}
