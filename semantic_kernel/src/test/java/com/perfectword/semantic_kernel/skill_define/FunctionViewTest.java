package com.perfectword.semantic_kernel.skill_define;


import com.perfectword.semantic_kernel.Kernel;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FunctionViewTest {

    @SKFunction(name = "GetTime")
    @SKDescription(description = "Get time static function")
    public static String getTime(@SKDescription(description = "year") @SKParameter(name = "year") String year) {
        return "2023";
    }

    @SKFunction(name = "GetYear")
    @SKDescription(description = "Get Year function")
    public String getYear(@SKParameter(name="year") String year) {
        return year + "Year";
    }

    @Test
    public void functionTest() throws NoSuchMethodException {
        ISKFunction function = new NativeFunction(
                this.getClass().getDeclaredMethod("getYear", String.class),
                this,
                "skill");
        Kernel kernel = new Kernel(new SkillCollection(), null, null, null, null);
        kernel.registerFunction(function);
        SKContext skContext = new SKContext(ContextVariables.of(), new NullMemory(), new SkillCollection());
        skContext.getVariables().set("year", Integer.toString(1024));
        skContext = function.invoke(skContext);
        Assertions.assertEquals(skContext.getVariables().getInput(), Integer.toString(1024) + "Year");
    }
}