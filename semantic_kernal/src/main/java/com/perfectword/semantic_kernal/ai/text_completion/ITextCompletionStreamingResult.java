package com.perfectword.semantic_kernal.ai.text_completion;

import java.util.concurrent.Flow;

public interface ITextCompletionStreamingResult {
    Flow.Publisher<String> getCompletionStreamingAsync();
}
