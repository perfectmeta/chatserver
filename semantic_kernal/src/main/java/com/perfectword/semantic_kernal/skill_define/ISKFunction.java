package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernal.orchestration.SKContext;

import java.util.concurrent.Future;
import java.util.function.Supplier;

public interface ISKFunction {
    String getName();
    String getSkillName();
    String getDescription();
    boolean isSemantic();
    CompleteRequestSettings getRequestSettings();
    FunctionView describe();
    Future<SKContext> invokeAsync(SKContext context, CompleteRequestSettings settings);
    Future<SKContext> invokeAsync(String input, CompleteRequestSettings settings);
    ISKFunction setDefaultSkillCollection(IReadOnlySkillCollection skills);
    ISKFunction setAIService(Supplier<ITextCompletion> serviceFactory);
    ISKFunction setAIConfiguration(CompleteRequestSettings settings);
}
