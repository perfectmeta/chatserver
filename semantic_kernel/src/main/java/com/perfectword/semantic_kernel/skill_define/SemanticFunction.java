package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;

import java.util.Objects;

public class SemanticFunction implements ISKFunction {
    private final FunctionView view;
    private final ITextCompletion textCompletion;

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
        this.textCompletion = textCompletion;
    }

    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        return null;
    }


}
