package chatserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {
        @Index(name = "byPhone", columnList = "phone", unique = true),
        @Index(name = "byEmail", columnList = "email", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Convert(converter = EUserType.Converter.class)
    private EUserType userType;
    private String botId;

    private String phone;
    private String email;
    private String nickName;
    private int gender;
    private String headIconUrl;

    private String englishName;
    private String location;
    private String personalizedSignature;
    private String birthDay;
    private String interest;
    private String disposition;
    private String favoriteFoods;
    private String unInterest;
    private String lifeGoal;
}
