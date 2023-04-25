package third.tts.entity.query.res;

public record Audio(String encoding, int sample_rate, int channels, int bit_depth, String audio) {}
