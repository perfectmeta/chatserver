package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.orchestration.SKContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NativeFunctionTest {

    @Test
    void view() throws NoSuchMethodException {
        ISKFunction function = new NativeFunction(
                this.getClass().getDeclaredMethod("getByYear", String.class),
                this,
                "skill");
        var view = function.view();
        assertEquals(view.name(), "getByYear");
        assertEquals(view.parameters().size(), 1);
        assertEquals(view.parameters().get(0).name(), "year");
    }

    @Test
    void invoke() throws NoSuchMethodException {
        ISKFunction function = new NativeFunction(
                this.getClass().getDeclaredMethod("getByYear", String.class),
                this,
                "skill");

        SKContext ctx = SKContext.of();
        ctx.getVariables().set("year", Integer.toString(1024));
        ctx = function.invoke(ctx);
        assertEquals(ctx.getVariables().getInput(), Integer.toString(1024) + "Year");
    }

    @Test
    void invokeStaticNoParameter() throws NoSuchMethodException {
        ISKFunction function = new NativeFunction(
                this.getClass().getDeclaredMethod("getYear"),
                null,
                "skill");

        SKContext ctx = SKContext.of();
        ctx.getVariables().set("input", Integer.toString(1024));
        ctx = function.invoke(ctx);
        assertEquals(ctx.getVariables().getInput(), "Year 2023");
    }

    @Test
    void invokeNoReturn() throws NoSuchMethodException {
        ISKFunction function = new NativeFunction(
                this.getClass().getDeclaredMethod("printYear"),
                this,
                "skill");

        SKContext ctx = SKContext.of();
        function.invoke(ctx);
    }

    @SKFunction(description = "GetYear")
    public String getByYear(@SKParameter(name = "year") String year) {
        return year + "Year";
    }

    @SKFunction(description = "get current year ")
    public static String getYear() {
        return "Year 2023";
    }

    @SKFunction(description = "get current year ")
    public void printYear() {
        System.out.println("Year 2023");
    }

}