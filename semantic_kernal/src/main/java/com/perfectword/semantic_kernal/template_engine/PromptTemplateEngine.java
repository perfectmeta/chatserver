package com.perfectword.semantic_kernal.template_engine;

import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.template_engine.blocks.Block;

import java.util.List;
import java.util.concurrent.Future;

// todo implement this class
public class PromptTemplateEngine implements IPromptTemplateEngine{
    @Override
    public List<Block> extractBlocks(String templateText, boolean validate) {
        return null;
    }

    @Override
    public List<Block> renderVariables(List<Block> blocks, ContextVariables variables) {
        return null;
    }

    @Override
    public Future<String> renderAsync(List<Block> blocks, SKContext context) {
        return null;
    }
}
