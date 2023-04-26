package chatserver.third.asr.entity.stream.req;

import chatserver.util.RecordBuilder;

public record Common(String app_id) {
    public static class Builder implements RecordBuilder<Common> {
        public String app_id;
        @Override
        public Common build() {
            return new Common(app_id);
        }
    }
}
