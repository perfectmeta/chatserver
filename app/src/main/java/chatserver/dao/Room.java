package chatserver.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;


    private String roomName;

    private long userId;
    private String userShowName;


    private int aiUserId;  // 1: 英语老师, 2：虚拟朋友

    private String aiShowName;


    private long createdTime;

    private long firstMessageId;

    private long lastMessageId;

    // 不要这里去聚合了，用单独的MessageRepository，因为将来有page的需求
//    @OneToMany
//    private List<Message> messages;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserShowName() {
        return userShowName;
    }

    public void setUserShowName(String userShowName) {
        this.userShowName = userShowName;
    }

    public int getAiUserId() {
        return aiUserId;
    }

    public void setAiUserId(int aiUserId) {
        this.aiUserId = aiUserId;
    }

    public String getAiShowName() {
        return aiShowName;
    }

    public void setAiShowName(String aiShowName) {
        this.aiShowName = aiShowName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getFirstMessageId() {
        return firstMessageId;
    }

    public void setFirstMessageId(long firstMessageId) {
        this.firstMessageId = firstMessageId;
    }

    public long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(long lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

}
