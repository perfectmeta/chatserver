package com.perfectword.semantic_kernal.memory;

import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class NullMemory implements ISemanticTextMemory{
    // todo to implement this class
    public static NullMemory Instance = new NullMemory();
    @Override
    public Future<String> saveInformationAsync(String collection, String text, String id, String description, String additionalMetadata) {
        return null;
    }

    @Override
    public Future<String> saveReferenceAsync(String collection, String text, String externalId, String externalSourceName, String description, String additionalMetadata) {
        return null;
    }

    @Override
    public Future<MemoryQueryResult> getAsync(String collection, String key, boolean withEmbedding) {
        return null;
    }

    @Override
    public Future<Void> removeAsync(String collection, String key) {
        return null;
    }

    @Override
    public Flow.Publisher<MemoryQueryResult> searchAsync(String collection, String query, int limit, double minRelevanceScore, boolean withEmbeddings) {
        return null;
    }
}
