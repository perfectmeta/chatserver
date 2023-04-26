package chatserver.third.tts.entity;


import chatserver.util.RecordBuilder;

import java.util.Objects;

record RequestHeader(String app_id) {
    public static class Builder implements RecordBuilder<RequestHeader> {
        public String app_id;
        public Builder() {
            app_id = "";
        }

        public RequestHeader build() {
            return new RequestHeader(app_id);
        }
    }
}

record RequestPayload(RequestText text) {
    public static class Builder implements RecordBuilder<RequestPayload> {
        public final RequestText.Builder text;
        public Builder() {
            text = new RequestText.Builder();
        }

        public RequestPayload build() {
            return new RequestPayload(text.build());
        }
    }
}

record RequestParameter(RequestDts dts) {
    public static class Builder implements RecordBuilder<RequestParameter> {
        public final RequestDts.Builder dts = new RequestDts.Builder();

        public RequestParameter build() {
            return new RequestParameter(dts.build());
        }
    }
}

record RequestText(String encoding, String compress, String format, String text) {
    public static class Builder {
        public String encoding;
        public String compress;
        public String format;
        public String text;
        public RequestText build() {
            return new RequestText(encoding, compress, format, text);
        }
    }
}

record RequestDts(String vcn, String language, int speed, int volume, int pitch, int rhy,
                  RequestAudio audio, RequestPybuf pybuf) {
    public static class Builder implements RecordBuilder<RequestDts> {
        public String vcn;
        public String language;
        public int speed;
        public int volume;
        public int pitch;
        public int rhy;
        public final RequestAudio.Builder audio = new RequestAudio.Builder();
        public final RequestPybuf.Builder pybuf = new RequestPybuf.Builder();
        @Override
        public RequestDts build() {
            return new RequestDts(vcn ,language, speed, volume, pitch, rhy, audio.build(), pybuf.build());
        }
    }
}

record RequestPybuf(String encoding, String compress, String format) {
    public static class Builder implements RecordBuilder<RequestPybuf> {
        public String encoding;
        public String compress;
        public String format;
        @Override
        public RequestPybuf build() {
            return new RequestPybuf(encoding, compress, format);
        }
    }
}

record RequestAudio(String encoding, int sample_rate) {
    public static class Builder implements RecordBuilder<RequestAudio>{
        public String encoding;
        public int sample_rate;

        @Override
        public RequestAudio build() {
            return new RequestAudio(encoding, sample_rate);
        }
    }
}

public record XFYLTTSRequestContent(RequestHeader header, RequestParameter parameter, RequestPayload payload){
    public static class Builder {
        public final RequestHeader.Builder header;
        public final RequestParameter.Builder parameter;
        public final RequestPayload.Builder payload;
        public Builder() {
            header = new RequestHeader.Builder();
            parameter = new RequestParameter.Builder();
            payload = new RequestPayload.Builder();
        }
        public XFYLTTSRequestContent build() {
            return new XFYLTTSRequestContent(header.build(), parameter.build(), payload.build());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (XFYLTTSRequestContent) obj;
        return Objects.equals(this.header, that.header) &&
                Objects.equals(this.parameter, that.parameter) &&
                Objects.equals(this.payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, parameter, payload);
    }

    @Override
    public String toString() {
        return "XFYLTTSRequestContent[" +
                "header=" + header + ", " +
                "parameter=" + parameter + ", " +
                "payload=" + payload + ']';
    }
}
