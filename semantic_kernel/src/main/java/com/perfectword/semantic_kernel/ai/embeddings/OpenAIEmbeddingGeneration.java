package com.perfectword.semantic_kernel.ai.embeddings;

import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class OpenAIEmbeddingGeneration implements IEmbeddingGeneration{
    private OpenAiService service;

    public OpenAIEmbeddingGeneration(OpenAiService service) {
        this.service = service;
    }

    @Override
    public List<Embedding> generateEmbeddings(List<String> values) {
        EmbeddingRequest er = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input(values)
                .build();
        var embeddings = service.createEmbeddings(er).getData();
        assert embeddings != null && embeddings.size() == values.size();
        return embeddings.stream().map(e->new Embedding(toFloatArray(e.getEmbedding()))).toList();
    }

    private float[] toFloatArray(List<Double> values) {
        var result = new float[values.size()];
        int i = 0;
        for (var d : values) {
            result[i++] = (float)d.doubleValue();
        }
        return result;
    }
}
