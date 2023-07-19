package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.KernelException;

import java.util.Collection;
import java.util.List;
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
            throw new KernelException(KernelException.ErrorCodes.FunctionNotAvailable,
                    "skill not available %s".formatted(skillName));

        }
        var function = sc.get(functionName);
        if (function == null) {
            throw new KernelException(KernelException.ErrorCodes.FunctionNotAvailable,
                    "Function not available %s.%s".formatted(skillName, functionName));
        }

        return function;
    }

    public Collection<String> getSkills() {
        return skillCollection.keySet();
    }

    public Collection<ISKFunction> getSkillFunctions(String skill) {
        ConcurrentHashMap<String, ISKFunction> map = skillCollection.get(skill);
        if (map != null) {
            return map.values();
        } else {
            return List.of();
        }
    }

}
