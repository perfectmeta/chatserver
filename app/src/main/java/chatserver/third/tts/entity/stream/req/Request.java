package chatserver.third.tts.entity.stream.req;

public record Request(Common common, Business business, Data data) {
    public static class Builder {
        public final Common.Builder common = new Common.Builder();
        public final Business.Builder business = new Business.Builder();
        public final Data.Builder data = new Data.Builder();
        public Request build() {
            return new Request(common.build(), business.build(), data.build());
        }
    }
}
