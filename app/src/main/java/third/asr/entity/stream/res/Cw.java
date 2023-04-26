package third.asr.entity.stream.res;

import util.RecordBuilder;

public record Cw(String w, int sc, int wb, int wc, int we, int wp) {
    public static class Builder implements RecordBuilder<Cw> {
        public String w;
        public int sc;
        public int wb;
        public int wc;
        int we;
        int wp;
        @Override
        public Cw build() {
            return new Cw(w, sc, wb, wc, we, wp);
        }
    }
}
