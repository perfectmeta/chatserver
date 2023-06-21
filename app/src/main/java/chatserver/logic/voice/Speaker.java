package chatserver.logic.voice;

public interface Speaker {
    byte[] getAudio(String content);
}
