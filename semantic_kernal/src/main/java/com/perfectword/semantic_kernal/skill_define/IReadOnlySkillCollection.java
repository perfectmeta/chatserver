package com.perfectword.semantic_kernal.skill_define;

public interface IReadOnlySkillCollection {
    ISKFunction getFunction(String functionName);
    ISKFunction getFunction(String skillName, String functionName);
    FunctionsView getFunctionsView(boolean includeSemantic, boolean includeNative);

    default FunctionsView getFunctionsView(boolean includeSemantic) {
        return getFunctionsView(includeSemantic, true);
    }

    default FunctionsView getFunctionsView() {
        return getFunctionsView(true);
    }
}
