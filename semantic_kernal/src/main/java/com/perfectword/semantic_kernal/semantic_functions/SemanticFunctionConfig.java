package com.perfectword.semantic_kernal.semantic_functions;


public class SemanticFunctionConfig {
    private final PromptTemplateConfig promptTemplateConfig;
    private final IPromptTemplate promptTemplate;

    public PromptTemplateConfig getPromptTemplateConfig() {
        return promptTemplateConfig;
    }

    public IPromptTemplate getPromptTemplate() {
        return promptTemplate;
    }

    public SemanticFunctionConfig(PromptTemplateConfig config, IPromptTemplate template) {
        promptTemplateConfig = config;
        promptTemplate = template;
    }
}
