package com.perfectword.semantic_kernel.ai.embeddings;

import java.util.List;

public interface IEmbeddingGeneration {

    public record Embedding(float[] vector) {
    }

    List<Embedding> generateEmbeddings(List<String> values);

    default Embedding generateEmbedding(String value) {
        List<Embedding> res = generateEmbeddings(List.of(value));
        return res.get(0);
    }
}
