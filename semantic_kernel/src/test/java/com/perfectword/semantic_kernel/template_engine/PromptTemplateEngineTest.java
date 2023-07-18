package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.NativeFunction;
import com.perfectword.semantic_kernel.skill_define.SKFunction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PromptTemplateEngineTest {


    private static PromptTemplateEngine engine;
    private static SKContext context;

    @BeforeAll
    static void init(){
        engine = new PromptTemplateEngine();
        context = SKContext.of();
        context.getVariables().set("style", "STYLE VALUE");
        context.getVariables().set("input", "INPUT VALUE");
    }

    @Test
    void renderValueAndVar() {
        var template = """
            WRITE EXACTLY ONE JOKE or HUMOROUS STORY ABOUT THE TOPIC BELOW

            JOKE MUST BE:
            - G RATED
            - WORKPLACE/FAMILY SAFE
            NO SEXISM, RACISM OR OTHER BIAS/BIGOTRY

            BE CREATIVE AND FUNNY. I WANT TO LAUGH.
            {{$style}}
            +++++

            {{$input}}
            {{'test value1'}}
            {{'test value2'}}
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
            test value1
            test value2
            +++++

                """;

        List<Block> blocks = engine.extractBlocks(template);
        var renderedTemplate = engine.render(blocks, context);
        assertEquals(result, renderedTemplate);
        System.out.println(renderedTemplate);
    }

    @Test
    void renderFunc() throws NoSuchMethodException {
        var template = "{{mySkill.myFunc}} hello";
        var result = "no input, return str hello";

        NativeFunction myFunc = new NativeFunction(PromptTemplateEngineTest.class.getDeclaredMethod("myFunc"), null, "mySkill");
        context.getSkills().addFunction(myFunc);

        List<Block> blocks = engine.extractBlocks(template);
        var renderedTemplate = engine.render(blocks, context);
        assertEquals(result, renderedTemplate);
        System.out.println(renderedTemplate);

    }

    @SKFunction(description = "my func")
    public static String myFunc(){
        return "no input, return str";
    }


    @Test
    void renderFuncWithArg() throws NoSuchMethodException {
        var template = "{{mySkill.myFuncWithArg $style}} hello";
        var result = "input STYLE VALUE, return str hello";

        NativeFunction myFunc = new NativeFunction(PromptTemplateEngineTest.class.getDeclaredMethod("myFuncWithArg", SKContext.class),
                null, "mySkill");
        context.getSkills().addFunction(myFunc);

        List<Block> blocks = engine.extractBlocks(template);
        var renderedTemplate = engine.render(blocks, context);
        assertEquals(result, renderedTemplate);
        System.out.println(renderedTemplate);
    }

    @SKFunction(description = "my func with arg")
    public static String myFuncWithArg(SKContext ctx){
        return "input %s, return str".formatted(ctx.getVariables().getInput());
    }


    @Test
    void renderFuncWithValArg() throws NoSuchMethodException {
        var template = "{{mySkill.myFuncWithArg 'value xxx'}} hello";
        var result = "input value xxx, return str hello";

        NativeFunction myFunc = new NativeFunction(PromptTemplateEngineTest.class.getDeclaredMethod("myFuncWithArg", SKContext.class),
                null, "mySkill");
        context.getSkills().addFunction(myFunc);

        List<Block> blocks = engine.extractBlocks(template);
        var renderedTemplate = engine.render(blocks, context);
        assertEquals(result, renderedTemplate);
        System.out.println(renderedTemplate);
    }



}