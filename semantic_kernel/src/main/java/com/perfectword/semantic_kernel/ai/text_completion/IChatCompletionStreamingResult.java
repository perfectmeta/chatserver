package com.perfectword.semantic_kernel.ai.text_completion;

import java.util.function.Consumer;

public interface IChatCompletionStreamingResult {
    void doOnError(Consumer<Throwable> onError);

    void blockingForEach(Consumer<String> onNext);
}
