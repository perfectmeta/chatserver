package chatserver.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contact", indexes = {
        @Index(name = "user_id_index", columnList = "user_id")
})
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "contact_user_id")
    private User contactUserId;
    private long createdTime;
}
