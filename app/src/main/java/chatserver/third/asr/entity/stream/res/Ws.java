package chatserver.third.asr.entity.stream.res;

import chatserver.util.RecordBuilder;

import java.util.ArrayList;
import java.util.List;

public record Ws(int bg, List<Cw> cw) {
    public static class Builder implements RecordBuilder<Ws> {
        public int bg;
        public List<Cw.Builder> cw = new ArrayList<>();
        @Override
        public Ws build() {
            return new Ws(bg, arrayBuild(cw));
        }
    }
}
