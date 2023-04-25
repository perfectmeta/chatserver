package third.tts.entity.create.req;

import java.util.Objects;

public record Request(Header header, Parameter parameter, Payload payload){
    public static class Builder {
        public final Header.Builder header;
        public final Parameter.Builder parameter;
        public final Payload.Builder payload;
        public Builder() {
            header = new Header.Builder();
            parameter = new Parameter.Builder();
            payload = new Payload.Builder();
        }
        public Request build() {
            return new Request(header.build(), parameter.build(), payload.build());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Request) obj;
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
