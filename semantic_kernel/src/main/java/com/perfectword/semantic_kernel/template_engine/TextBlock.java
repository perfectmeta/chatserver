package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

public final class TextBlock implements Block {
    private final String content;

    public TextBlock(String content) {
        this.content = content;
    }

    @Override
    public String render(SKContext ctx) {
        return content;
    }
}