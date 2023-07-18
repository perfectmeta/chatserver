package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;

public sealed interface Block permits TextBlock, ValBlock, VarBlock, FuncBlock {

    String render(SKContext ctx);

}
