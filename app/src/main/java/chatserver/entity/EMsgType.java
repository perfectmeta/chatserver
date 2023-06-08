package chatserver.entity;

import chatserver.gen.MsgType;

// 这里跟proto里保持一致，能不能直接用proto里的呢，感觉还是再定义一遍安心，毕竟这是在db层
public enum EMsgType implements EnumBase {
    AUDIO_WITH_TEXT(0, MsgType.AUDIO_WITH_TEXT),
    TEXT_WITH_AUDIO(1, MsgType.TEXT_WITH_AUDIO),
    TEXT(2, MsgType.TEXT),
    IMAGE(3, MsgType.IMAGE),
    VIDEO(4, MsgType.VIDEO),
    UNRECOGNIZED(-1, MsgType.UNRECOGNIZED);

    private final int val;
    private final MsgType protoVal;

    EMsgType(int v, MsgType protoVal) {
        this.val = v;
        this.protoVal = protoVal;
    }

    @Override
    public int getValue() {
        return val;
    }

    public static class Converter extends ConverterBase<EMsgType> {

        public Converter() {
            super(EMsgType.UNRECOGNIZED);
        }
    }

    public MsgType toProto() {
        return protoVal;
    }
}
