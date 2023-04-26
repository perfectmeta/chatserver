package chatserver.dao;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    private long roomId;

    private int authorUserType;

    private long authorUserId;
    private String authorShowName;

    private long createdTime;

    private int msgType;

    private String text;

    private String audioUrl;

    private String imageUrl;

    private String videoUrl;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getAuthorUserType() {
        return authorUserType;
    }

    public void setAuthorUserType(int authorUserType) {
        this.authorUserType = authorUserType;
    }
    
    public long getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(long authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getAuthorShowName() {
        return authorShowName;
    }

    public void setAuthorShowName(String authorShowName) {
        this.authorShowName = authorShowName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


}
