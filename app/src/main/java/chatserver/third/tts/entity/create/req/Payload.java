package chatserver.third.tts.entity.create.req;

import chatserver.util.RecordBuilder;

record Payload(Text text) {
    public static class Builder implements RecordBuilder<Payload> {
        public final Text.Builder text;
        public Builder() {
            text = new Text.Builder();
        }

        public Payload build() {
            return new Payload(text.build());
        }
    }
}

