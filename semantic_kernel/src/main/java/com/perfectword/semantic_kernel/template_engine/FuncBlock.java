package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.ISKFunction;

public final class FuncBlock implements Block {
    private final String skillName;
    private final String functionName;

    private final Block parameter;  // var block or val block

    public FuncBlock(String skillName, String functionName, Block parameter) {
        this.skillName = skillName;
        this.functionName = functionName;

        if (!(parameter instanceof VarBlock) && !(parameter instanceof ValBlock)) {
            throw new KernelException(ErrorCodes.FunctionTypeNotSupported,
                    "function %s.%s not support type %s".formatted(
                            skillName, functionName, parameter.getClass().toString()));
        }
        this.parameter = parameter;
    }

    public FuncBlock(String content) {
        String[] parts = content.split("\\.");
        if (parts.length != 2) {
            throw new KernelException(ErrorCodes.TemplateSyntaxError,
                    "function name %s not match syntax [skillName].[functionName]".formatted(content));
        }
        skillName = parts[0];
        functionName = parts[1];
        parameter = null;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Block getParameter() {
        return parameter;
    }

    @Override
    public String render(SKContext ctx) {
        ISKFunction function = ctx.getSkills().getFunction(skillName, functionName);

        SKContext ctxCopy = ctx.copy();

        if (parameter != null) {
            String input = parameter.render(ctxCopy);
            ctxCopy.getVariables().setInput(input);
        }


        try {
            ctxCopy = function.invoke(ctxCopy);
        } catch (Exception e) {
            ctxCopy.fail(e.getMessage(), e);
        }

        if (ctxCopy.isErrorOccurred()) {
            throw new KernelException(ErrorCodes.FunctionInvokeError,
                    "function %s.%s invoke failed".formatted(skillName, functionName),
                    ctxCopy.getLastException());
        }
        return ctxCopy.getVariables().getInput();
    }

}
