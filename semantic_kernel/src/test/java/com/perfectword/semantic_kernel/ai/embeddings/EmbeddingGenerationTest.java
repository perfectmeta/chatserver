package com.perfectword.semantic_kernel.ai.embeddings;

import com.perfectword.semantic_kernel.util.OpenAI;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingGenerationTest {

    private static float[] array;

    @Test
    void baseTest() {
        var service = OpenAI.makeOpenAiService();
        OpenAIEmbeddingGeneration embeddingGeneration = new OpenAIEmbeddingGeneration(service);
        var result = embeddingGeneration.generateEmbeddings(List.of("I'am a coder", "I have a keyboard"));
        assertEquals(result.size(), 2);
        for (var r : result) {
            System.out.println(r.vector().length);
            System.out.println(floatArrayToString(r.vector()));
        }
    }

    public static String floatArrayToString(float[] array) {
        EmbeddingGenerationTest.array = array;
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < array.length; i++) {
            sb.append(Float.toString(array[i]));

            if (i != array.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}