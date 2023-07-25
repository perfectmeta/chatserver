package chatserver.config;

public class RobotConfig {
    private final String id;
    private final String name;
    private final String description;
    private final String model;
    private final String speaker;
    private final String greeting;
    private final String headIcon;
    private final String artistModel;
    private final PromptConfig promptConfig;

    public RobotConfig(String id,
                       String name,
                       String description,
                       String model,
                       String speaker,
                       String greeting,
                       String promptStr,
                       String headIcon,
                       String artistModel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.model = model;
        this.speaker = speaker;
        this.greeting = greeting;
        this.promptConfig = PromptConfig.parse(promptStr);
        this.headIcon = headIcon;
        this.artistModel = artistModel;
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

    public String getModel() {
        return model;
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

    public String getArtistModel() {
        return artistModel;
    }
}
