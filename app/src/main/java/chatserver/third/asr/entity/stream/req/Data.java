package chatserver.third.asr.entity.stream.req;

import chatserver.util.RecordBuilder;

public record Data(int status, String format, String encoding, String audio) {
    public static class Builder implements RecordBuilder<Data> {
        public int status;
        public String format;
        public String encoding;
        public String audio;
        @Override
        public Data build() {
            return new Data(status, format, encoding, audio);
        }
    }
}
