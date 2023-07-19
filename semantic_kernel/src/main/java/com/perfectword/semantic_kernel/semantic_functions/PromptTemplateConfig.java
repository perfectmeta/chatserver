package com.perfectword.semantic_kernel.semantic_functions;

import java.util.List;

public record PromptTemplateConfig(int schema,
                                   String type,
                                   String description,
                                   CompletionConfig completion,
                                   List<String> defaultServices,
                                   InputConfig input) {

    public record CompletionConfig(double temperature,
                                   double topP,
                                   double presencePenalty,
                                   double frequencyPenalty,
                                   int maxTokens,
                                   List<String> stopSequences) {

        static final CompletionConfig INSTANCE = new CompletionConfig(1, 0.5,
                0, 0, 100, List.of());

        public static CompletionConfig of() {
            return INSTANCE;
        }
    }

    public record InputParameter(String name,
                                 String description,
                                 String defaultValue) {
    }

    public record InputConfig(List<InputParameter> parameters) {
    }
}
