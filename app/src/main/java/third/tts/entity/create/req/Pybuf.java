package third.tts.entity.create.req;

import third.tts.entity.RecordBuilder;

public record Pybuf(String encoding, String compress, String format) {
    public static class Builder implements RecordBuilder<Pybuf> {
        public String encoding;
        public String compress;
        public String format;
        @Override
        public Pybuf build() {
            return new Pybuf(encoding, compress, format);
        }
    }
}
