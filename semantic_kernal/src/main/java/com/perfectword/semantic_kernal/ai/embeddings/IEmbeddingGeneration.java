package com.perfectword.semantic_kernal.ai.embeddings;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public interface IEmbeddingGeneration<TValue, TEmbedding> {
    Future<List<Embedding<TEmbedding>>> generateEmbeddingsAsync(List<TValue> data);

    default Future<Embedding<TEmbedding>> generateEmbeddingAsync(
            TValue value) {
        var listResult = this.generateEmbeddingsAsync(List.of(value));
        var newTask = new FutureTask<>(()->listResult.get().stream().findFirst().orElseGet(null));
        Thread.startVirtualThread(newTask);
        return newTask;
    }
}
