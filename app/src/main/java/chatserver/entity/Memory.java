package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memoryId;
    private long userId;
    private long otherUserId;
    private int memoType;  // 为将来扩展
    @Column(length = 1022)
    private String memo;

    private long createdTime;
}
