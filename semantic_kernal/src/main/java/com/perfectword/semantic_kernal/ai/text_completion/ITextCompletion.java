package com.perfectword.semantic_kernal.ai.text_completion;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public interface ITextCompletion {
    Future<List<ITextCompletionResult>> getCompletionsAsync(
            String text,
            CompleteRequestSettings requestSettings
    );

    Flow.Publisher<ITextCompletionStreamingResult> getStreamingCompletionsAsync(
            String text,
            CompleteRequestSettings requestSettings
    );
}
