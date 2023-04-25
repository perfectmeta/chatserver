package third.tts.entity.create.req;

public record Text(String encoding, String compress, String format, String text) {
    public static class Builder {
        public String encoding;
        public String compress;
        public String format;
        public String text;
        public Text build() {
            return new Text(encoding, compress, format, text);
        }
    }
}
