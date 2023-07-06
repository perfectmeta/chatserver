package com.perfectword.semantic_kernal.orchestration;

import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.skill_define.ReadOnlySkillCollection;

public class SKContext {
    public ContextVariables getVariables() {
        return variables;
    }

    public ISemanticTextMemory getMemory() {
        return memory;
    }

    public ReadOnlySkillCollection getSkills() {
        return skills;
    }

    private ContextVariables variables;
    private ISemanticTextMemory memory;
    private ReadOnlySkillCollection skills;
}
