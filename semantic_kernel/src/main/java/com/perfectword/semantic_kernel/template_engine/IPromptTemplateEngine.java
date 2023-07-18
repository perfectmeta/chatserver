package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.util.List;

public interface IPromptTemplateEngine {
    List<Block> extractBlocks(String templateText);

    String render(List<Block> blocks, SKContext context);
}
