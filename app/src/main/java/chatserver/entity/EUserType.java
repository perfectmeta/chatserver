package chatserver.entity;

public enum EUserType implements EnumBase {
    HUMAN(0),
    BOT(1),
    UNRECOGNIZED(-1);

    private final int val;

    EUserType(int v) {
        this.val = v;
    }

    @Override
    public int getValue() {
        return val;
    }

    public static class Converter extends ConverterBase<EUserType> {

        public Converter() {
            super(EUserType.UNRECOGNIZED);
        }
    }
}
