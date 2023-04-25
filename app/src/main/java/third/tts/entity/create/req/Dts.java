package third.tts.entity.create.req;

import third.tts.entity.RecordBuilder;

public record Dts(String vcn, String language, int speed, int volume, int pitch, int rhy,
                  Audio audio, Pybuf pybuf) {
    public static class Builder implements RecordBuilder<Dts> {
        public String vcn;
        public String language;
        public int speed;
        public int volume;
        public int pitch;
        public int rhy;
        public final Audio.Builder audio = new Audio.Builder();
        public final Pybuf.Builder pybuf = new Pybuf.Builder();
        @Override
        public Dts build() {
            return new Dts(vcn ,language, speed, volume, pitch, rhy, audio.build(), pybuf.build());
        }
    }
}
