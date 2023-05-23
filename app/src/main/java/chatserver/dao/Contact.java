package chatserver.dao;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long contactId;
    //private long userId;
    //private long contactUserId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User contact;
    private long createdTime;
}
