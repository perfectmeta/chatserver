package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;

import java.util.Map;
import java.util.function.Function;

public class KernelConfig {
    // todo implement this class

    public final String DefaultServiceId = "__SK_DEFAULT";

    public Map<String, Function<IKernel, ITextCompletion>> textCompletionServices;

    public Map<String, Function<IKernel, ITextCompletion>> getTextCompletionServices() {
        return textCompletionServices;
    }
}
