package com.perfectword.semantic_kernal.orchestration;

import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.skill_define.SkillCollection;

public class SKContext {
    private ContextVariables variables;
    private ISemanticTextMemory memory;
    private SkillCollection skills;

    public ContextVariables getVariables() {
        return variables;
    }

    public ISemanticTextMemory getMemory() {
        return memory;
    }

    public SkillCollection getSkills() {
        return skills;
    }
}
