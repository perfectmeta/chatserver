package third.tts.entity.query.req;

import third.tts.entity.RecordBuilder;

public record Request(Header header) {
    public static class Builder implements RecordBuilder<Request> {
        public final Header.Builder header = new Header.Builder();
        @Override
        public Request build() {
            return new Request(header.build());
        }
    }
}
