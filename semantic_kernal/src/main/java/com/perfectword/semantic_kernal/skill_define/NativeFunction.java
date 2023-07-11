package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.orchestration.SKContext;

import java.lang.reflect.Method;

public class NativeFunction implements ISKFunction {

    private FunctionView view;


    public static NativeFunction of(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {
        // TODO
        return null;
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
