package com.perfectword.semantic_kernal.memory;

import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public interface SemanticTextMemory {
    Future<String> saveInformationAsync(
            String collection,
            String text,
            String id,
            String description,
            String additionalMetadata
    );

    Future<String> saveReferenceAsync(
            String collection,
            String text,
            String externalId,
            String externalSourceName,
            String description,
            String additionalMetadata
    );

    Future<MemoryQueryResult> getAsync(
            String collection,
            String key,
            boolean withEmbedding
    );

    Future<Void> removeAsync(
            String collection,
            String key
    );

    Flow.Publisher<MemoryQueryResult> searchAsync(
            String collection,
            String query,
            int limit,
            double minRelevanceScore,
            boolean withEmbeddings
    );

    default Flow.Publisher<MemoryQueryResult> searchAsync(
            String collection,
            String query
    ) {
        return searchAsync(collection, query, 1, 0.7, false);
    }
}
