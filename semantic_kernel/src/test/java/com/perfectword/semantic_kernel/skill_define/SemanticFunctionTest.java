package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.KernelConfig;
import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;
import com.perfectword.semantic_kernel.util.OpenAI;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SemanticFunctionTest {
    static final String joke_prompt = """
            WRITE EXACTLY ONE JOKE or HUMOROUS STORY ABOUT THE TOPIC BELOW

            JOKE MUST BE:
            - G RATED
            - WORKPLACE/FAMILY SAFE
            NO SEXISM, RACISM OR OTHER BIAS/BIGOTRY

            BE CREATIVE AND FUNNY. I WANT TO LAUGH.
            {{$style}}
            +++++

            {{$input}}
            +++++

            """;
    static final String joke_config_json = """
            {
              "schema": 1,
              "description": "Generate a funny joke",
              "type": "completion",
              "completion": {
                "max_tokens": 1000,
                "temperature": 0.8,
                "top_p": 0.0,
                "presence_penalty": 0.0,
                "frequency_penalty": 0.0
              },
              "input": {
                "parameters": [
                  {
                    "name": "input",
                    "description": "Joke subject",
                    "default_value": ""
                  }
                ]
              }
            }
            """;

    @Test
    void view() {
    }

    @Test
    void invoke() throws IOException {
        var promptTemplateConfig = KernelConfig.parseSkillConfigJson(joke_config_json);
        var promptTemplate = new PromptTemplate(joke_prompt, promptTemplateConfig, new PromptTemplateEngine());
        var skFunction = new SemanticFunction("FunSkill",
                "Joke",
                promptTemplate,
                new OpenAITextCompletion(OpenAI.makeOpenAiService(), "text-davinci-003"));
        SKContext ctx = SKContext.of();
        ctx.getVariables().setInput("Hahaha");
        ctx.getVariables().set("style", "funny");

        ctx = skFunction.invoke(ctx);
        System.out.println(ctx.getVariables().getInput());
    }
}