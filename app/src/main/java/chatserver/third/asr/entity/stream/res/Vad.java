package chatserver.third.asr.entity.stream.res;

import chatserver.util.RecordBuilder;

import java.util.ArrayList;
import java.util.List;

public record Vad(List<Ws> ws, int bg, int ed, float eg) {
    public static class Builder implements RecordBuilder<Vad> {
        public final List<Ws.Builder> ws = new ArrayList<>();
        public int bg;
        public int ed;
        public float eg;
        @Override
        public Vad build() {
            return new Vad(arrayBuild(ws), bg, ed, eg);
        }
    }
}
