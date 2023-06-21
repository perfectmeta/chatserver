package chatserver.logic.voice;

import chatserver.third.tts.Pwrdtts;

import java.util.Objects;

public class PwrdSpeaker implements Speaker{
    private final String speakerName;
    public PwrdSpeaker(String speakerName) {
        this.speakerName = speakerName;
    }

    @Override
    public byte[] getAudio(String content) {
        // todo refactor to use speaker
        Objects.requireNonNull(speakerName);
        return Pwrdtts.tts(content);
    }
}
