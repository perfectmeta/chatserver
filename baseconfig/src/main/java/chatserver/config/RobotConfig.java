package chatserver.config;

public class RobotConfig {
    private final String id;
    private final String name;
    private final String description;
    private final String speaker;
    private final String greeting;

    private final String headIcon;

    private final PromptConfig promptConfig;

    public RobotConfig(String id,
                       String name,
                       String description,
                       String speaker,
                       String greeting,
                       String promptStr,
                       String headIcon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.speaker = speaker;
        this.greeting = greeting;
        this.promptConfig = PromptConfig.parse(promptStr);
        this.headIcon = headIcon;
    }

    public String getId() {
        return id;

    }

    public String getName() {
        return name;

    }

    public String getDescription() {
        return description;

    }

    public String getSpeaker() {
        return speaker;
    }

    public String getGreeting() {
        return greeting;
    }

    public PromptConfig getPrompt() {
        return promptConfig;
    }


    public int getGender() {
        return 1; // female
    }

    public String getHeadIcon() {
        return headIcon;
    }

}
