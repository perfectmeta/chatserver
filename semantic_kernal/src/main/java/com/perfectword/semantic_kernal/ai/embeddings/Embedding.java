package com.perfectword.semantic_kernal.ai.embeddings;

public class Embedding<TEmbedding> {
    private static Embedding EMPTY = new Embedding(null);
    private TEmbedding[] vector;

    public Embedding(Iterable<TEmbedding> vector) {
        // todo
        // this.vector = vector.
    }

}
