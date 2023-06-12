package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {
        @Index(name = "byUserId", columnList = "userId"),
        @Index(name = "byAIType", columnList = "aiType"),
        @Index(name = "byAIUserId", columnList = "aiUserId")
})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    private String roomName;

    @Convert(converter = EUserType.Converter.class)
    private EUserType userType;
    private long userId;
    private String userShowName;

    @Convert(converter = EUserType.Converter.class)
    private EUserType aiType;
    private long aiUserId;
    private String aiShowName;

    private long createdTime;

    private long firstMessageId;
    private long lastMessageId;

    private int memoryFree;
}
