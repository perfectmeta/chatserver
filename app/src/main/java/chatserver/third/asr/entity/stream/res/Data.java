package chatserver.third.asr.entity.stream.res;

import chatserver.util.RecordBuilder;

public record Data(int status, Result result) {
    public static class Builder implements RecordBuilder<Data> {
        public int status;
        public final Result.Builder result = new Result.Builder();

        @Override
        public Data build() {
            return new Data(status, result.build());
        }
    }
}
