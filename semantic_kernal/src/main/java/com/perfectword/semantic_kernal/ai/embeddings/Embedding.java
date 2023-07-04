package com.perfectword.semantic_kernal.ai.embeddings;

import java.util.Arrays;
import java.util.Iterator;

public class Embedding<TEmbedding> {
    private static Embedding EMPTY = new Embedding();
    private TEmbedding[] vector;

    public Embedding(Iterable<TEmbedding> vector) {
        // todo
        // this.vector = vector.
    }

}
