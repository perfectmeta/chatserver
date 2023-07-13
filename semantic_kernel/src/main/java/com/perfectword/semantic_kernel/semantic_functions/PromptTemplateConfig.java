package com.perfectword.semantic_kernel.semantic_functions;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public record PromptTemplateConfig(int schema,
                                   String type,
                                   String description,
                                   CompletionConfig completion,
                                   List<String> defaultServices,
                                   InputConfig input) {
    public static PromptTemplateConfig fromJson(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(path.toFile(), PromptTemplateConfig.class);
    }

    public static PromptTemplateConfig fromJson(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, PromptTemplateConfig.class);
    }

    public record CompletionConfig(double temperature,
                                   double topP,
                                   double presencePenalty,
                                   double frequencyPenalty,
                                   int maxTokens,
                                   List<String> stopSequences) {

    }

    public record InputParameter(String name,
                                 String description,
                                 String defaultValue) {
    }

    public record InputConfig(List<InputParameter> parameters) {
    }
}
