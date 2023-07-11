package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.Verify;
import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.PromptTemplate;

public class SemanticFunction implements ISKFunction{

    private FunctionView view;

    public static SemanticFunction of(
            String skillName,
            String functionName,
            PromptTemplate promptTemplate,
            ITextCompletion textCompletion
    ) {
        Verify.validSkillName(skillName);
        Verify.ValidFunctionName(functionName);
        var view = new FunctionView(functionName, skillName, "", true,
                promptTemplate.getParameters());
        // todo
        return null;
    }

    @Override
    public FunctionView view() {
        return null;
    }

    @Override
    public SKContext invoke(SKContext context) {
        return null;
    }


}
