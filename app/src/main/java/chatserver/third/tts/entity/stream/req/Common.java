package chatserver.third.tts.entity.stream.req;

public record Common(String app_id) {
    public static class Builder {
        public String app_id;
        public Common build() {
            return new Common(app_id);
        }
    }
}
