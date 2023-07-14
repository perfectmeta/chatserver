package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.template_engine.blocks.Block;

import java.util.List;
import java.util.concurrent.Future;

public interface IPromptTemplateEngine {
    List<Block> extractBlocks(String templateText, boolean validate);

    default List<Block> extractBlocks(String templateText) {
        return extractBlocks(templateText, true);
    }

    List<Block> renderVariables(List<Block> blocks, ContextVariables variables);

    String render(List<Block> blocks, SKContext context);

    String render(String templateText, SKContext context);
}
