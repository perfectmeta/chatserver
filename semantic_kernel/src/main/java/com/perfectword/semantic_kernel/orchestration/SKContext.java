package com.perfectword.semantic_kernel.orchestration;

import com.perfectword.semantic_kernel.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.skill_define.SkillCollection;

public class SKContext {
    private final ContextVariables variables;
    private final ISemanticTextMemory memory;
    private final SkillCollection skills;
    private boolean errorOccurred;
    private String lastErrorDescription;
    private Exception lastException;

    public SKContext(ContextVariables variables, ISemanticTextMemory memory, SkillCollection skills) {
        this.variables = variables;
        this.memory = memory;
        this.skills = skills;
    }

    public static SKContext of() {
        return new SKContext(ContextVariables.of(), NullMemory.getInstance(), new SkillCollection());
    }

    public SKContext copy() {
        SKContext ctx = new SKContext(variables.copy(), memory, skills);
        ctx.errorOccurred = errorOccurred;
        ctx.lastErrorDescription = lastErrorDescription;
        ctx.lastException = lastException;
        return ctx;
    }

    public ContextVariables getVariables() {
        return variables;
    }

    public ISemanticTextMemory getMemory() {
        return memory;
    }

    public SkillCollection getSkills() {
        return skills;
    }


    public void fail(String errorDescription, Exception exception) {
        errorOccurred = true;
        lastErrorDescription = errorDescription;
        lastException = exception;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public String getLastErrorDescription() {
        return lastErrorDescription;
    }

    public Exception getLastException() {
        return lastException;
    }


}
