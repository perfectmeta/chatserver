package chatserver.third.asr.entity.stream.req;

import chatserver.util.RecordBuilder;

public record Request(Common common, Business business, Data data) {
    public static class Builder implements RecordBuilder<Request> {
        public final Common.Builder common = new Common.Builder();
        public final Business.Builder business = new Business.Builder();
        public final Data.Builder data = new Data.Builder();
        @Override
        public Request build() {
            return new Request(common.build(), business.build(), data.build());
        }
    }
}
