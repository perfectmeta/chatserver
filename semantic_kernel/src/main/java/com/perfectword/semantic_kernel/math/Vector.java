package com.perfectword.semantic_kernel.math;

public class Vector {
    public static float dotProduct(float[] vector1, float[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vector lengths do not match");
        }

        float dotProduct = 0.0f;
        for (int i = 0, end = vector1.length; i < end; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        return dotProduct;
    }

    public static float cosineSimilarity(float[] vector1, float[] vector2) {
        return dotProduct(vector1, vector2) / (magnitude(vector1) * magnitude(vector2));
    }

    public static float magnitude(float[] vector) {
        float magnitude = 0.0f;
        for (float value : vector) {
            magnitude += value * value;
        }

        return (float) Math.sqrt(magnitude);
    }

    public static double magnitude(double[] vector) {
        double magnitude = 0.0;
        for (double value : vector) {
            magnitude += value * value;
        }

        return Math.sqrt(magnitude);
    }
}
