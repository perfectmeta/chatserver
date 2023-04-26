package chatserver.third.asr.entity.stream.res;

import chatserver.util.RecordBuilder;

import java.util.ArrayList;
import java.util.List;

public record Result(int sn, boolean ls, int bg, int ed, List<Ws> ws, String pgs, List<Integer> rg, Vad vad) {
    public static class Builder implements RecordBuilder<Result> {
        public int sn;
        public boolean ls;
        public int bg;
        public int ed;
        public List<Ws.Builder> ws = new ArrayList<>();
        public String pgs;
        public List<Integer> rg = new ArrayList<>();
        public Vad.Builder vad = new Vad.Builder();

        @Override
        public Result build() {
            return new Result(sn, ls, bg, ed, arrayBuild(ws), pgs, rg, vad.build());
        }
    }
}
