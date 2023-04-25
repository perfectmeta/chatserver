package third.tts.entity;

public class LTTSResponse {
    public LTTSResponse(int code) {
        this.code = code;
    }

    public int code;
    public String message;
    public byte[] rawContent;
}
