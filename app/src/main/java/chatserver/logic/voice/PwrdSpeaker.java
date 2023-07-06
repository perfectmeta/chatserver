package chatserver.logic.voice;

import chatserver.third.tts.Pwrdtts;

import java.util.Objects;

public class PwrdSpeaker implements Speaker{
    private final String speakerName;
    public PwrdSpeaker(String speaker) {
        this.speakerName = speaker;
    }

    @Override
    public byte[] getAudio(String content) {
        return Pwrdtts.tts(content, speakerName);
    }
}
