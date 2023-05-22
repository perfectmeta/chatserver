package chatserver.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BotClass {
    @Id
    private long botClassId;
    private String botClassName;
    private int gender;
    private String tags;
    @Column(length = 2046)
    private String prompt;
    private String description;
}
