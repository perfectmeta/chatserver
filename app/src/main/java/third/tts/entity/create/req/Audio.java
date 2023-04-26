package third.tts.entity.create.req;

import util.RecordBuilder;

public record Audio(String encoding, int sample_rate) {
    public static class Builder implements RecordBuilder<Audio> {
        public String encoding;
        public int sample_rate;

        @Override
        public Audio build() {
            return new Audio(encoding, sample_rate);
        }
    }
}

