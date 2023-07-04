package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.KernelException;

import java.util.concurrent.ConcurrentHashMap;

public class SkillCollection implements ISkillCollection {

    public static final String GLOBAL_SKILL = "_GLOBAL_SKILL_";
    private IReadOnlySkillCollection readOnlySkillCollection;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ISKFunction>> skillCollection;

    public SkillCollection() {
        readOnlySkillCollection = new ReadOnlySkillCollection(this);
        skillCollection = new ConcurrentHashMap<>();
    }

    public IReadOnlySkillCollection getReadOnlySkillCollection() {
        return readOnlySkillCollection;
    }

    @Override
    public ISkillCollection addFunction(ISKFunction functionInstance) {
        ConcurrentHashMap<String, ISKFunction> skill = skillCollection.compute(functionInstance.getSkillName(), (k, v)->{
            if (v == null) {
                v = new ConcurrentHashMap<>();
            }
            v.put(functionInstance.getSkillName(), functionInstance);
            return v;
        });
        return null;
    }

    @Override
    public ISKFunction getFunction(String functionName) {
        return getFunction(GLOBAL_SKILL, functionName);
    }

    @Override
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

    @Override
    public FunctionsView getFunctionsView(boolean includeSemantic, boolean includeNative) {
        var result = new FunctionsView();
        if (includeSemantic || includeNative) {
            for(var skillPair : skillCollection.entrySet()) {
                for (var functionPair : skillPair.getValue().entrySet()) {
                    if ((functionPair.getValue().isSemantic() && includeSemantic) || includeNative) {
                        result.addFunction(functionPair.getValue().describe());
                    }
                }
            }
        }
        return result;
    }

    private void throwFunctionNotAvailable(String skillName, String functionName) {
        throw new KernelException(KernelException.ErrorCodes.FunctionNotAvailable,
                "Function not available %s.%s".formatted(skillName, functionName));
    }
}
