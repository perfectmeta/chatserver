package chatserver.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@SuppressWarnings("unused")
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

    public long getBotClassId() {
        return botClassId;
    }

    public void setBotClassId(long botClassId) {
        this.botClassId = botClassId;
    }

    public String getBotClassName() {
        return botClassName;
    }

    public void setBotClassName(String botClassName) {
        this.botClassName = botClassName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
