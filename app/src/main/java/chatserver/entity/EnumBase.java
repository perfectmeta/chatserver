package chatserver.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public interface EnumBase {
    int getValue();


    @Converter
    abstract class ConverterBase<T extends Enum<T> & EnumBase> implements AttributeConverter<T, Integer> {
        private final T defaultEnum;

        public ConverterBase(T defaultEnum) {
            this.defaultEnum = defaultEnum;
        }

        @Override
        public Integer convertToDatabaseColumn(T attribute) {
            return attribute != null ? attribute.getValue() : defaultEnum.getValue();
        }

        @Override
        public T convertToEntityAttribute(Integer dbData) {
            T[] enums = defaultEnum.getDeclaringClass().getEnumConstants();
            for (T e : enums) {
                if (e.getValue() == dbData) {
                    return e;
                }
            }
            return defaultEnum;
        }
    }


}
