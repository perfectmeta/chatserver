package chatserver.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserCategory {
    @Id
    private long userCategoryId;

    private int userType; // 0: human , 1: bot
    private String userCategoryName;
    private int gender; // 0: unknown, 1: male, 2: female
    private String tags;
    private String description;

    @Column(length = 2046)
    private String prompt;
}
