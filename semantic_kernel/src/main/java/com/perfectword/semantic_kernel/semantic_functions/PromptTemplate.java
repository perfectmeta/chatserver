package com.perfectword.semantic_kernel.semantic_functions;

import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.ParameterView;
import com.perfectword.semantic_kernel.template_engine.IPromptTemplateEngine;

import java.util.List;

public class PromptTemplate {
    private final String template;
    private final IPromptTemplateEngine templateEngine;
    private final PromptTemplateConfig promptConfig;

    public PromptTemplate(String template,
                          PromptTemplateConfig promptTemplateConfig,
                          IPromptTemplateEngine promptTemplateEngine) {
        this.template = template;
        this.templateEngine = promptTemplateEngine;
        this.promptConfig = promptTemplateConfig;
    }


    public String getTemplate() {
        return template;
    }

    public IPromptTemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public PromptTemplateConfig getPromptConfig() {
        return promptConfig;
    }

    public List<ParameterView> getParameters() {
        return promptConfig.input().parameters().stream()
                .map(p -> new ParameterView(p.name(), p.description())).toList();
    }

    public String render(SKContext executionContext) {
        return templateEngine.render(template, executionContext);
    }
}