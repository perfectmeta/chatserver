package third.asr.entity.stream.req;

import util.RecordBuilder;

public record Business(String language, String domain, String accent, int vad_eos, String dwa, String pd,
                       int ptt, String rlang, int vinfo, int nunum, int speex_size, int nbest, int wbest) {
    public static class Builder implements RecordBuilder<Business> {
        public String language;
        public String domain;
        public String accent;
        public int vad_eos;
        public String dwa;
        public String pd;
        public int ptt;
        public String rlang;
        public int vinfo;
        public int nunum;
        public int speex_size;
        public int nbest;
        public int wbest;
        @Override
        public Business build() {
            return new Business(language, domain, accent, vad_eos, dwa, pd, ptt, rlang, vinfo,
                    nunum, speex_size, nbest, wbest);
        }
    }
}
