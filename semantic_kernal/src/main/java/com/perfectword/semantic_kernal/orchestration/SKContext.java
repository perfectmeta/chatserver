package com.perfectword.semantic_kernal.orchestration;

import com.perfectword.semantic_kernal.ai.AIException;
import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.skill_define.ReadOnlySkillCollection;

public class SKContext {
    // todo implement this class
    private ContextVariables variables;
    private ISemanticTextMemory memory;
    private ReadOnlySkillCollection skills;
    private boolean errorOccurred;
    private String lastErrorDescription = "";
    private Exception lastException = null;

    public Exception getLastException() {
        return lastException;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public String getLastErrorDescription() {
        return lastErrorDescription;
    }

    public ContextVariables getVariables() {
        return variables;
    }

    public ISemanticTextMemory getMemory() {
        return memory;
    }

    public ReadOnlySkillCollection getSkills() {
        return skills;
    }

    public SKContext fail(String message, Exception ex) {
        errorOccurred = true;
        lastErrorDescription = message;
        lastException = ex;
        return this;
    }
}
