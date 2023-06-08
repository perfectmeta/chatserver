package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;
    private long roomId;

    @Convert(converter = EUserType.Converter.class)
    private EUserType authorUserType;
    private long authorUserId;
    private String authorShowName;

    @Convert(converter = EMsgType.Converter.class)
    private EMsgType msgType;
    private String text;
    private String audioUrl;
    private String imageUrl;
    private String videoUrl;

    private long createdTime;
}
