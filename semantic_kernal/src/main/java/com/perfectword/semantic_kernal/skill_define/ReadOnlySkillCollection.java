package com.perfectword.semantic_kernal.skill_define;

public class ReadOnlySkillCollection implements IReadOnlySkillCollection{
    private final ISkillCollection skillCollection;

    public ReadOnlySkillCollection(ISkillCollection skillCollection) {
        this.skillCollection = skillCollection;
    }

    @Override
    public ISKFunction getFunction(String functionName) {
        return skillCollection.getFunction(functionName);
    }

    @Override
    public ISKFunction getFunction(String skillName, String functionName) {
        return skillCollection.getFunction(skillName, functionName);
    }

    @Override
    public FunctionsView getFunctionsView(boolean includeSemantic, boolean includeNative) {
        return skillCollection.getFunctionsView(includeSemantic, includeNative);
    }
}
