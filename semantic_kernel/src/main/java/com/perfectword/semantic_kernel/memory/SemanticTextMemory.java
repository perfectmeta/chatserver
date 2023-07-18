package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;
import com.perfectword.semantic_kernel.ai.embeddings.IEmbeddingGeneration;

import java.time.OffsetDateTime;
import java.util.List;

public final class SemanticTextMemory implements ISemanticTextMemory, AutoCloseable {
    private final IEmbeddingGeneration embeddingGeneration;
    private final IMemoryStore storage;

    public SemanticTextMemory(IEmbeddingGeneration embeddingGeneration, IMemoryStore storage) {
        this.embeddingGeneration = embeddingGeneration;
        this.storage = storage;
    }

    @Override
    public void saveInformation(String collection, String text, String id, String description, String additionalMetadata) {
        Embedding embedding = embeddingGeneration.generateEmbedding(text);
        MemoryRecord record = MemoryRecord.localRecord(id, text, description, embedding,
                additionalMetadata, "", OffsetDateTime.now());
        if (!storage.doesCollectionExist(collection)) {
            storage.createCollection(collection);
        }
        storage.upsert(collection, record);
    }

    @Override
    public MemoryQueryResult get(String collection, String key, boolean withEmbedding) {
        MemoryRecord record = storage.get(collection, key);
        return record == null ? null : MemoryQueryResult.fromMemoryRecord(record, 1.0f);
    }

    public List<String> getCollections() {
        return storage.getCollections();
    }

    @Override
    public void remove(String collection, String key) {
        storage.remove(collection, key);
    }

    @Override
    public List<MemoryQueryResult> search(String collection, String query, int limit, double minRelevanceScore, boolean withEmbeddings) {
        Embedding queryEmbedding = embeddingGeneration.generateEmbedding(query);
        List<MatchResult> matchResults = storage.getNearestMatches(collection, queryEmbedding, limit, minRelevanceScore, withEmbeddings);
        return matchResults.stream().map(r->
                        MemoryQueryResult.fromMemoryRecord(r.record(), (float)r.confidence()))
                .toList();
    }

    @Override
    public void close() throws Exception {
        if (embeddingGeneration instanceof AutoCloseable) {
            ((AutoCloseable) embeddingGeneration).close();
        }

        if (storage instanceof AutoCloseable) {
            ((AutoCloseable) storage).close();
        }
    }
}
