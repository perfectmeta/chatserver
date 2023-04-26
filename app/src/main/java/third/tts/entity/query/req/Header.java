package third.tts.entity.query.req;

import util.RecordBuilder;

record Header(String app_id, String task_id) {
    public static class Builder implements RecordBuilder<Header> {
        public String app_id;
        public String task_id;
        @Override
        public Header build() {
            return new Header(app_id, task_id);
        }
    }
}

