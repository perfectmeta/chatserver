package third.asr.entity.stream.res;

import util.RecordBuilder;

import java.util.ArrayList;
import java.util.List;

public record Result(int sn, boolean ls, int bg, int ed, List<Ws> ws) {
    public static class Builder implements RecordBuilder<Result> {
        public int sn;
        public boolean ls;
        public int bg;
        public int ed;
        public List<Ws.Builder> ws = new ArrayList<>();
        @Override
        public Result build() {
            return new Result(sn, ls, bg, ed, arrayBuild(ws));
        }
    }
}
