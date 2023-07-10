package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernal.orchestration.SKContext;

import java.lang.reflect.Method;
import java.util.List;

import static com.perfectword.semantic_kernal.Util.nullOrWhitespace;

public class SKFunction implements ISKFunction {

    private FunctionView view;


    public static SKFunction fromNativeMethod(
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
