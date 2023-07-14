package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromptTemplateEngineTest {

    @Test
    void render() {
        var template = """
            WRITE EXACTLY ONE JOKE or HUMOROUS STORY ABOUT THE TOPIC BELOW

            JOKE MUST BE:
            - G RATED
            - WORKPLACE/FAMILY SAFE
            NO SEXISM, RACISM OR OTHER BIAS/BIGOTRY

            BE CREATIVE AND FUNNY. I WANT TO LAUGH.
            {{$style}}
            +++++

            {{input}}
            +++++

                """;
        var result = """
            WRITE EXACTLY ONE JOKE or HUMOROUS STORY ABOUT THE TOPIC BELOW

            JOKE MUST BE:
            - G RATED
            - WORKPLACE/FAMILY SAFE
            NO SEXISM, RACISM OR OTHER BIAS/BIGOTRY

            BE CREATIVE AND FUNNY. I WANT TO LAUGH.
            STYLE VALUE
            +++++

            INPUT VALUE
            +++++

                """;
        PromptTemplateEngine engine = new PromptTemplateEngine();
        SKContext context = SKContext.of();
        context.getVariables().set("style", "STYLE VALUE");
        context.getVariables().set("input", "INPUT VALUE");
        var renderedTemplate = engine.render(template, context);
        assertEquals(result, renderedTemplate);
        System.out.println(renderedTemplate);
    }
}