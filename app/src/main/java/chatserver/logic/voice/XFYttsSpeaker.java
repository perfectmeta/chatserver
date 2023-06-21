package chatserver.logic.voice;

import chatserver.third.tts.XFYtts;

import java.io.IOException;

public class XFYttsSpeaker implements Speaker{
    @Override
    public byte[] getAudio(String content) {
        try (var audioStream = XFYtts.makeSession(content)) {
            return audioStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
