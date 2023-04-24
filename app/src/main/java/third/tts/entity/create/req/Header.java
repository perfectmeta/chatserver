package third.tts.entity.create.req;

import third.tts.entity.RecordBuilder;

public record Header(String app_id) {
    public static class Builder implements RecordBuilder<Header> {
        public String app_id;
        public Builder() {
            app_id = "";
        }

        public Header build() {
            return new Header(app_id);
        }
    }
}
