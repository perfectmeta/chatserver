package third.asr.entity.stream.res;

public record Response(String sid, int code, String message, Data data) {
}
