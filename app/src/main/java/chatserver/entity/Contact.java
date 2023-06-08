package chatserver.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(ContactKey.class)
@Table(indexes = {
        @Index(name = "bySubjectUserId", columnList = "subjectUserId")
})
public class Contact {
    @Id
    private long subjectUserId;

    @Id
    private long objectUserId;

    private long createdTime;
}
