package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserCategory {
    // TODO 删除
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userCategoryId;

    private int userType; // 0: human , 1: bot
    private String userCategoryName;
    private int gender; // 0: unknown, 1: male, 2: female
    private String tags;
    private String description;

    @Column(length = 8190)
    private String prompt;
}
