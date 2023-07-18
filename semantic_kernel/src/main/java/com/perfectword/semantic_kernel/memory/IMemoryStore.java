package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.util.List;

public interface IMemoryStore {
    void createCollection(String collectionName);
    List<String> getCollections();
    boolean doesCollectionExist(String collectionName);
    void deleteCollection(String collectionName);
    String upsert(String collectionName, MemoryRecord record);
    List<String> upsertBatch(String collectionName, List<MemoryRecord> records);
    List<MemoryRecord> getBatch(String collectionName, List<String> keys, boolean withEmbeddings);
    default List<MemoryRecord> getBatch(String collectionName, List<String> keys) {
        return getBatch(collectionName, keys, false);
    }

    void remove(String collectionName, String key);
    void removeBatch(String collectionName, List<String> keys);

    List<MatchResult> getNearestMatches(String collectionName, Embedding embedding, int limit,
                                        double minRelevanceScore, boolean withEmbeddings);

    default List<MatchResult> getNearestMatches(String collectionName, Embedding embedding, int limit,
                                                double minRelevanceScore) {
        return getNearestMatches(collectionName, embedding, limit, minRelevanceScore, false);
    }

    default List<MatchResult> getNearestMatches(String collectionName, Embedding embedding, int limit) {
        return getNearestMatches(collectionName, embedding, limit, 0.0, false);
    }
}
