package chatserver.third.tts.entity.stream.res;

public record Response(int code, String message, String sid, Data data) {
}
