package com.perfectword.semantic_kernel.ai.text_completion;

import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import io.reactivex.Flowable;

import java.util.function.Consumer;

public class OpenAIChatCompletionStreamingResult implements IChatCompletionStreamingResult {
    private Flowable<ChatCompletionChunk> chatResult;

    public OpenAIChatCompletionStreamingResult(Flowable<ChatCompletionChunk> chatResult) {
        this.chatResult = chatResult;
    }

    @Override
    public void doOnError(Consumer<Throwable> onError) {
        chatResult = chatResult.doOnError(onError::accept);
    }

    @Override
    public void blockingForEach(Consumer<String> onNext) {
        chatResult.blockingForEach(t -> onNext.accept(t.getChoices().get(0).getMessage().getContent()));
    }
}
