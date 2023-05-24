package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {
        @Index(name = "phone", columnList = "phone", unique = true),
        @Index(name = "email", columnList = "email", unique = true)
})
public class User {  // user is human or bot
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private int userCategory;
    private String phone;
    private String email;
    private String nickName;

    private String headIconUrl;
}
