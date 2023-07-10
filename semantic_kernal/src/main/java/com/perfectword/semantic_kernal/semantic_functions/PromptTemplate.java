package com.perfectword.semantic_kernal.semantic_functions;

import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.skill_define.ParameterView;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.List;
import java.util.concurrent.Future;

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

    public List<ParameterView> getParameters() {
        return null;
    }

    public String render(SKContext executionContext) {
        return null;
    }
}
