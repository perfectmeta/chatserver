package com.perfectword.semantic_kernel.ai.embeddings;

import com.perfectword.semantic_kernel.math.Vector;

public record Embedding(float[] vector) {
    public static final float FLOAT_EPSILON = 1.0f / 10000000000f;
    public static final double DOUBLE_EPSILON = 1.0 / 10000000000.0;
    public static final Embedding EMPTY = new Embedding(null);

    public boolean isEmpty() {
        return vector == null || vector.length == 0;
    }

    public int length() {
        return vector == null ? 0 : vector.length;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Embedding)) {
            return false;
        }
        if (isEmpty() && ((Embedding) other).isEmpty()) {
            return true;
        }
        if (isEmpty() || ((Embedding) other).isEmpty()) {
            return false;
        }

        for (var i = 0; i < vector.length; i++) {
            if (vector[i] * ((Embedding) other).vector[i] < 0.0f
                    || Math.abs(vector[i] - ((Embedding) other).vector[i]) < Embedding.FLOAT_EPSILON ) {
                return false;
            }
        }
        return true;
    }

    public double cosineSimilarity(Embedding embedding) {
        return Vector.cosineSimilarity(vector, embedding.vector);
    }
}
