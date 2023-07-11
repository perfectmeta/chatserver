package com.perfectword.semantic_kernal.ai.text_completion;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public interface ITextCompletion {
    List<ITextCompletionResult> getCompletions(
            String text,
            CompleteRequestSettings requestSettings
    );

    ITextCompletionStreamingResult getStreamingCompletions(
            String text,
            CompleteRequestSettings requestSettings
    );
}
