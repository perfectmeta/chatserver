package chatserver.third.tts.entity.create.req;

import chatserver.util.RecordBuilder;

public record Parameter(Dts dts) {
    public static class Builder implements RecordBuilder<Parameter> {
        public final Dts.Builder dts = new Dts.Builder();

        public Parameter build() {
            return new Parameter(dts.build());
        }
    }
}

