package chatserver.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {@Index(name = "byUserId", columnList = "userId"), @Index(name = "byAIType", columnList = "aiType"), @Index(name = "byAIUserId", columnList = "aiUserId")})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    private String roomName;

    private long userType;
    private long userId;
    private String userShowName;

    private long aiType;

    private long aiUserId;

    private String aiShowName;

    private long createdTime;

    private long firstMessageId;

    private long lastMessageId;

    // 不要这里去聚合了，用单独的MessageRepository，因为将来有page的需求
//    @OneToMany
//    private List<Message> messages;
}
