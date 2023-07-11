package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.orchestration.SKContext;

public interface ISKFunction {
    FunctionView view();

    SKContext invoke(SKContext context);
}
