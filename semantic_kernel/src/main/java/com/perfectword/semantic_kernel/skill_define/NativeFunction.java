package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.lang.reflect.Method;

public class NativeFunction implements ISKFunction {

    private FunctionView view;


    public NativeFunction(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {

    }


    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        return null;
    }
}
