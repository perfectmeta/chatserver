package com.perfectword.semantic_kernel.ai.text_completion;


import com.perfectword.semantic_kernel.semantic_functions.PromptTemplateConfig;

public record CompleteRequestSettings(PromptTemplateConfig.CompletionConfig config,
                                      int resultPerPrompt) {

    public static CompleteRequestSettings of() {
        return new CompleteRequestSettings(PromptTemplateConfig.CompletionConfig.of(), 1);
    }
}

