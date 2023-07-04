package com.perfectword.semantic_kernal.template_engine;

import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.template_engine.blocks.Block;

import java.util.List;
import java.util.concurrent.Future;

public interface IPromptTemplateEngine {
    List<Block> extractBlocks(String templateText, boolean validate);

    default List<Block> extractBlocks(String templateText) {
        return extractBlocks(templateText, true);
    }

    List<Block> renderVariables(List<Block> blocks, ContextVariables variables);

    Future<String> renderAsync(List<Block> blocks, SKContext context);
}
