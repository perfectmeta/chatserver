package com.perfectword.semantic_kernel.ai.embeddings;

import com.perfectword.semantic_kernel.math.Vector;

public record Embedding(float[] vector) {
    public static final Embedding EMPTY = new Embedding(null);

    public double cosineSimilarity(Embedding embedding) {
        return Vector.cosineSimilarity(vector, embedding.vector);
    }
}
