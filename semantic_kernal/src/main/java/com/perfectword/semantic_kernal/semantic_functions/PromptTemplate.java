package com.perfectword.semantic_kernal.semantic_functions;

import com.perfectword.semantic_kernal.IKernel;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.skill_define.ParameterView;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.List;
import java.util.concurrent.Future;

public class PromptTemplate implements IPromptTemplate{

    private final String template;
    private final IPromptTemplateEngine templateEngine;
    private final PromptTemplateConfig promptConfig;

    public PromptTemplate(String template,
                          PromptTemplateConfig promptTemplateConfig,
                          IKernel kernel) {
        this(template, promptTemplateConfig, kernel.getPromptTemplateEngine());
    }


    public PromptTemplate(String template,
                          PromptTemplateConfig promptTemplateConfig,
                          IPromptTemplateEngine promptTemplateEngine) {
        this.template = template;
        this.templateEngine = promptTemplateEngine;
        this.promptConfig = promptTemplateConfig;
    }

    @Override
    public List<ParameterView> getParameters() {
        return null;
    }

    @Override
    public Future<String> renderAsync(SKContext executionContext) {
        return null;
    }
}
