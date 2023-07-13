package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernel.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;

import java.util.Objects;

public class SemanticFunction implements ISKFunction {
    private final FunctionView view;
    private final ITextCompletion textCompletion;
    private final PromptTemplate promptTemplate;

    public SemanticFunction(String skillName,
                            String functionName,
                            PromptTemplate promptTemplate,
                            ITextCompletion textCompletion) {
        Verify.validSkillName(skillName);
        Verify.ValidFunctionName(functionName);
        Objects.requireNonNull(promptTemplate);
        Objects.requireNonNull(textCompletion);
        view = new FunctionView(functionName, skillName, promptTemplate.getPromptConfig().description(),
                true, promptTemplate.getParameters());
        this.promptTemplate = promptTemplate;
        this.textCompletion = textCompletion;
    }

    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        String prompt = promptTemplate.render(context);
        var setting = new CompleteRequestSettings(promptTemplate.getPromptConfig().completion(), 1);
        String result = textCompletion.getCompletion(prompt, setting);
        context.getVariables().setInput(result);
        return context;
    }
}
