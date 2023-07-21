package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

public final class ValBlock implements Block {
    private final String val;

    public ValBlock(String content) {
        //content 两侧包含引号
        this.val = content.substring(1, content.length() - 1);
    }

    @Override
    public String render(SKContext ctx) {
        return val;
    }
}
