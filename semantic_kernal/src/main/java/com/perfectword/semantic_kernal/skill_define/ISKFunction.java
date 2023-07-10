package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.orchestration.SKContext;

public interface ISKFunction {
    FunctionView view();

    SKContext invoke(SKContext context);
}
