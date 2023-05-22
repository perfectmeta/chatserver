package chatserver.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
// @Table(name = "memory_node")
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long me;
    private long you;
    @Column(length = 2048)
    private String memory;

    private long first_encounter_time;
    private long last_communication_time;
}
