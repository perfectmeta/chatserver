package chatserver.third.asr.entity.stream.req;

import chatserver.util.RecordBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record Request(Common common, Business business, Data data) {
    public static class Builder implements RecordBuilder<Request> {
        private Common.Builder common = new Common.Builder();
        private Business.Builder business = new Business.Builder();
        private Data.Builder data = new Data.Builder();

        @Override
        public Request build() {
            return new Request(common.build(), business.build(), data.build());
        }

        public Common.Builder getCommon() {
            if (common == null) {
                common = new Common.Builder();
            }
            return common;
        }

        public Business.Builder getBusiness() {
            if (business == null) {
                business = new Business.Builder();
            }
            return business;
        }

        public Data.Builder getData() {
            if (data == null) {
                data = new Data.Builder();
            }
            return data;
        }
    }
}
