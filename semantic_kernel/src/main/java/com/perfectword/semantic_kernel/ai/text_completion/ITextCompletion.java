package com.perfectword.semantic_kernel.ai.text_completion;

public interface ITextCompletion {
    String getCompletion(
            String text,
            CompleteRequestSettings requestSettings
    );
}
