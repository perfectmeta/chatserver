package com.perfectword.semantic_kernal.ai.text_completion;


import com.perfectword.semantic_kernal.semantic_functions.PromptTemplateConfig;

public record CompleteRequestSettings(PromptTemplateConfig.CompletionConfig config,
                                      int resultPerPrompt) {
}

