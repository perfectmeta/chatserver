package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.KernelException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionsView {
    private final ConcurrentHashMap<String, List<FunctionView>> semanticFunctions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<FunctionView>> nativeFunctions = new ConcurrentHashMap<>();

    public FunctionsView addFunction(FunctionView view) {
        var functions = view.isSemantic() ? semanticFunctions : nativeFunctions;
        functions.compute(view.getSkillName(), (k, v)->{
            if (v == null) {
                v = new ArrayList<>();
            }
            v.add(view);
            return v;
        });
        return this;
    }

    static class FunctionType {
        boolean isSemantic;
        boolean isNative;
    }

    public boolean isSemantic(String skillName, String functionName) {
        return searchFunctionType(skillName, functionName).isSemantic;
    }

    public boolean isNative(String skillName, String functionName) {
        return searchFunctionType(skillName, functionName).isNative;
    }

    private FunctionType searchFunctionType(String skillName, String functionName) {
        var functionType = new FunctionType();
        semanticFunctions.computeIfPresent(skillName, (k, v)->{
            functionType.isSemantic = v.stream().anyMatch(s->s.getName().equals(functionName));
            return v;
        });

        nativeFunctions.computeIfPresent(skillName, (k, v)->{
            functionType.isNative = v.stream().anyMatch(s->s.getName().equals(functionName));
            return v;
        });
        if (functionType.isSemantic && functionType.isNative) {
            throw new KernelException(KernelException.ErrorCodes.AmbiguousMatchFunctionType,
                    "%s:%s has both type semantic and native.".formatted(skillName, functionName));
        }
        return functionType;
    }
}
