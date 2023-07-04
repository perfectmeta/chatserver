package com.perfectword.semantic_kernal.semantic_functions;

import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.skill_define.ParameterView;

import java.util.List;
import java.util.concurrent.Future;

public interface PromptTemplate {
    List<ParameterView> getParameters();
    Future<String> renderAsync(SKContext executionContext);
}
