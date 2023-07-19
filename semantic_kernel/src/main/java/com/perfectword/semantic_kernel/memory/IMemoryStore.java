package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.util.List;

public interface IMemoryStore extends AutoCloseable {
    void createCollection(String collectionName);

    List<String> getCollections();

    boolean doesCollectionExist(String collectionName);

    void deleteCollection(String collectionName);

    MemoryRecord upsert(String collectionName, MemoryRecord record);

    List<MemoryRecord> upsertBatch(String collectionName, List<MemoryRecord> records);

    List<MemoryRecord> getBatch(String collectionName, List<String> keys, boolean withEmbeddings);

    default MemoryRecord get(String collectionName, String key) {
        var batchResult = getBatch(collectionName, List.of(key), false);
        return batchResult.isEmpty() ? null : batchResult.get(0);
    }

    void remove(String collectionName, String key);

    void removeBatch(String collectionName, List<String> keys);

    List<MemoryQueryResult> getNearestMatches(String collectionName, Embedding embedding, int limit,
                                              double minRelevanceScore);

    default List<MemoryQueryResult> getNearestMatches(String collectionName, Embedding embedding, int limit) {
        return getNearestMatches(collectionName, embedding, limit, 0.0);
    }

    @Override
    default void close() throws Exception {
    }
}
