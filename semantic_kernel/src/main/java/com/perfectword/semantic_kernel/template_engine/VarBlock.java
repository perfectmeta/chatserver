package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

public final class VarBlock implements Block {

    private final String varName;

    public VarBlock(String content) {
        //content包含$，这里去掉
        this.varName = content.substring(1);
    }

    @Override
    public String render(SKContext ctx) {
        String val = ctx.getVariables().get(varName);
        return val != null ? val : "";
    }
}
