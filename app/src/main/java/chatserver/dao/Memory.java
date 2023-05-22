package chatserver.dao;

import jakarta.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public long getMe() {
        return me;
    }

    public void setMe(long me) {
        this.me = me;
    }

    public long getYou() {
        return you;
    }

    public void setYou(long you) {
        this.you = you;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public long getLast_communication_time() {
        return last_communication_time;
    }

    public void setLast_communication_time(long last_communication_time) {
        this.last_communication_time = last_communication_time;
    }

    public long getFirst_encounter_time() {
        return first_encounter_time;
    }

    public void setFirst_encounter_time(long first_encounter_time) {
        this.first_encounter_time = first_encounter_time;
    }
}
