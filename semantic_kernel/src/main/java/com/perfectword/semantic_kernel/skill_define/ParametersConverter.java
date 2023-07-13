package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParametersConverter {
    private List<Function<String, ?>> converters;

    public Object[] convert(Object[] params) {
        assert(converters.size() == params.length);
        Object[] result = new Object[params.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = converters.get(i).apply((String)params[i]);
        }
        return result;
    }

    private ParametersConverter() {}

    public static ParametersConverter of(Method method) {
        var converter = new ParametersConverter();
        List<Function<String, ?>> converters = new ArrayList<>();
        for (var p : method.getParameters()) {
            var pType = p.getType();
            if (pType == SKContext.class) {
                converters.add(x->x);
            } else if (pType == String.class) {
                converters.add(x->x);
            } else if (pType == int.class || pType == Integer.class) {
                converters.add(Integer::parseInt);
            } else if (pType == float.class || pType == Float.class) {
                converters.add(Float::parseFloat);
            } else if (pType == double.class || pType == Double.class) {
                converters.add(Double::parseDouble);
            } else if (Object.class.isAssignableFrom(pType)) {
                converters.add(x->x);
            }
        }
        converter.converters = converters;
        return converter;
    }
}
