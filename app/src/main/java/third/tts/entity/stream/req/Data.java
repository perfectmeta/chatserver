package third.tts.entity.stream.req;

import third.tts.entity.RecordBuilder;

public record Data(String text, int status) {
    public static class Builder implements RecordBuilder<Data> {
        public String text;
        public int status;
        @Override
        public Data build() {
            return new Data(text, status);
        }
    }
}
