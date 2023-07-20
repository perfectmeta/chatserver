package com.perfectword.semantic_kernel.semantic_functions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

public record PromptTemplateConfig(int schema,
                                   String type,
                                   String description,
                                   CompletionConfig completion,
                                   List<String> defaultServices,
                                   @JsonDeserialize
                                   InputConfig input) {
    public PromptTemplateConfig(int schema,
                                   String type,
                                   String description,
                                   CompletionConfig completion,
                                   List<String> defaultServices,
                                   InputConfig input) {
        this.schema = schema;
        this.type = type;
        this.completion = completion;
        this.description = description;
        this.defaultServices = defaultServices == null ? new ArrayList<>() : defaultServices;
        this.input = input == null ? new InputConfig(new ArrayList<>()) : input;
    }

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
