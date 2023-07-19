package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;
import com.perfectword.semantic_kernel.ai.embeddings.IEmbeddingGeneration;

import java.util.List;

public final class SemanticTextMemory implements ISemanticTextMemory {
    private final IEmbeddingGeneration embeddingGeneration;
    private final IMemoryStore storage;

    public SemanticTextMemory(IEmbeddingGeneration embeddingGeneration, IMemoryStore storage) {
        this.embeddingGeneration = embeddingGeneration;
        this.storage = storage;
    }

    @Override
    public void saveInformation(String collection, String text, String id, String description, String additionalMetadata) {
        Embedding embedding = embeddingGeneration.generateEmbedding(text);
        MemoryRecord record = MemoryRecord.ofLocal(id, text, description, additionalMetadata, embedding, null, null);
        if (!storage.doesCollectionExist(collection)) {
            storage.createCollection(collection);
        }
        storage.upsert(collection, record);
    }

    @Override
    public MemoryQueryResult get(String collection, String key, boolean withEmbedding) {
        MemoryRecord record = storage.get(collection, key);
        return record == null ? null : new MemoryQueryResult(record, 1.0f);
    }

    @Override
    public void remove(String collection, String key) {
        storage.remove(collection, key);
    }

    @Override
    public List<MemoryQueryResult> search(String collection, String query, int limit, double minRelevanceScore) {
        Embedding queryEmbedding = embeddingGeneration.generateEmbedding(query);
        return storage.getNearestMatches(collection, queryEmbedding, limit, minRelevanceScore);
    }

    @Override
    public void close() throws Exception {
        storage.close();
    }
}
