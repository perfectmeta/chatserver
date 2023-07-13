package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.Kernel;
import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplateConfig;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;
import org.junit.jupiter.api.Test;
import com.perfectword.semantic_kernel.util.OpenAI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SemanticFunctionTest {

    @Test
    void view() {
    }

    @Test
    void invoke() throws IOException {
        var prompt = """
                Please answer me using the input value.
                input: {{input}}
                answer: 
                """;
        var promptTemplateConfig = PromptTemplateConfig.fromJson("");
        var promptTemplate = new PromptTemplate(prompt, promptTemplateConfig, new PromptTemplateEngine());
        var skFunction = new SemanticFunction("defaultName",
                "functionName",
                promptTemplate,
                new OpenAITextCompletion(OpenAI.makeOpenAiService(), "gpt-3.5-turbo"));
        SKContext skContext = new SKContext(ContextVariables.of(), new NullMemory(), new SkillCollection());
        skContext.getVariables().setInput("Hahaha");
        skContext = skFunction.invoke(skContext);
        assertEquals("Hahaha", skContext.getVariables().getInput());
    }
}