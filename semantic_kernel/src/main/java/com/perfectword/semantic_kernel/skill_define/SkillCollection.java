package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.KernelException;

import java.util.concurrent.ConcurrentHashMap;

public class SkillCollection {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ISKFunction>> skillCollection = new ConcurrentHashMap<>();

    public void addFunction(ISKFunction functionInstance) {
        skillCollection.compute(functionInstance.view().skillName(), (k, v) -> {
            if (v == null) {
                v = new ConcurrentHashMap<>();
            }
            v.put(functionInstance.view().name(), functionInstance);
            return v;
        });
    }

    public ISKFunction getFunction(String skillName, String functionName) {
        var sc = skillCollection.get(skillName);
        if (sc == null) {
            throwFunctionNotAvailable(skillName, functionName);
        }
        var function = sc.get(functionName);
        if (function == null) {
            throwFunctionNotAvailable(skillName, functionName);
        }

        return function;
    }

    private static void throwFunctionNotAvailable(String skillName, String functionName) {
        throw new KernelException(KernelException.ErrorCodes.FunctionNotAvailable,
                "Function not available %s.%s".formatted(skillName, functionName));
    }
}
