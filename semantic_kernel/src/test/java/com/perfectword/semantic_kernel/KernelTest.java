package com.perfectword.semantic_kernel;

import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.*;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

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
}