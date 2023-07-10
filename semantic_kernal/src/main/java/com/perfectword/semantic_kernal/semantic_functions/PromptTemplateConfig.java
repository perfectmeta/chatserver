package com.perfectword.semantic_kernal.semantic_functions;

import java.util.List;

public record PromptTemplateConfig(int schema, String type, String description, CompletionConfig completion,
                                   List<String> defaultServices, InputConfig input) {

    public record CompletionConfig(float temperature, float topP, float presencePenalty, float frequencyPenalty,
                                   int maxTokens, List<String> stopSequences) {

    }

    public record InputParameter(String name, String description, String defaultValue) {
    }

    public record InputConfig(List<InputParameter> parameters) {
    }
}
