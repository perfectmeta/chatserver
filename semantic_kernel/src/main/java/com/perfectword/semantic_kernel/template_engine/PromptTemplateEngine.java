package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.util.List;

public class PromptTemplateEngine implements IPromptTemplateEngine {

    private final TemplateTokenizer tokenizer = new TemplateTokenizer();

    @Override
    public List<Block> extractBlocks(String templateText) {
        return tokenizer.extractBlocks(templateText);
    }

    @Override
    public String render(List<Block> blocks, SKContext context) {
        var result = new StringBuilder();
        for (Block block : blocks) {
            result.append(block.render(context));
        }
        return result.toString();
    }

}
