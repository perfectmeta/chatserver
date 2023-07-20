package com.perfectword.semantic_kernel;

import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.*;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;
import com.perfectword.semantic_kernel.util.OpenAI;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class KernelTest {
    Kernel kernel;

    @SKFunction(name = "sayHello", description = "")
    public void sayHello(@SKParameter(name = "name") String name) {
        System.out.println("Hello");
    }

    @Test
    @Order(1)
    void registerFunction() throws NoSuchMethodException {
        Method method = KernelTest.class.getMethod("sayHello", String.class);
        ISKFunction function = new NativeFunction(method,this, "defaultSkill");
        kernel = new Kernel(new SkillCollection(),
                null, null, null, new PromptTemplateEngine());
        kernel.registerFunction(function);
    }

    @Test
    @Order(2)
    void runFunction() {
        SKContext context = SKContext.of();
        context.getVariables().set("name", "You");
        // kernel.runFunction(context.getVariables(), )
    }

    @Test
    @Order(3)
    void importFromDirTest() throws IOException {
        var skillDir = Path.of("./src/test/resources/");
        var service = OpenAI.makeOpenAiService();
        var kernel = new Kernel(new SkillCollection(), NullMemory.getInstance(),
                new OpenAITextCompletion(service, ""), null, new PromptTemplateEngine());
        kernel.importSkillFromDirectory(skillDir);
        var function = kernel.skills().getFunction("DefaultSkill", "ChatGPT");
        assertNotNull(function);
    }
}