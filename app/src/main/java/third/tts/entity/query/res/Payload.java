package third.tts.entity.query.res;

import third.tts.entity.RecordBuilder;

public record Payload(Audio audio, Pybuf pybuf) {
    public static class Builder implements RecordBuilder<Payload> {

        @Override
        public Payload build() {
            return null;
        }
    }
}
