package third.tts.entity.stream.req;

public record Business(String aue, int sfl, String auf, String vcn, int speed, int volume, int pitch,
                       int bgs, String tte, String reg, String rdn) {
    public static class Builder {
        public String aue;
        public int sfl;
        public String auf;
        public String vcn;
        public int speed;
        public int volume;
        public int pitch;
        public int bgs;
        public String tte;
        public String reg;
        public String rdn;

        public Business build() {
            return new Business(aue, sfl, auf, vcn, speed, volume, pitch, bgs, tte, reg, rdn);
        }
    }
}
