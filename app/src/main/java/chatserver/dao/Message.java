package chatserver.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
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
}
