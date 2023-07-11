package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.PromptTemplate;
import com.perfectword.semantic_kernal.semantic_functions.PromptTemplateConfig;

public class SemanticSKFunction implements ISKFunction{

    public static SemanticSKFunction of(
            String skillName,
            String functionName,
            PromptTemplate promptTemplate,
            ITextCompletion textCompletion
    ) {
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
