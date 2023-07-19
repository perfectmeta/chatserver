package com.perfectword.semantic_kernel.memory;

import java.util.List;

public interface ISemanticTextMemory extends AutoCloseable {
    void saveInformation(
            String collection,
            String text,
            String id,
            String description,
            String additionalMetadata
    );


    MemoryQueryResult get(
            String collection,
            String key,
            boolean withEmbedding
    );

    void remove(
            String collection,
            String key
    );

    List<MemoryQueryResult> search(
            String collection,
            String query,
            int limit,
            double minRelevanceScore
    );

    default List<MemoryQueryResult> search(
            String collection,
            String query
    ) {
        return search(collection, query, 1, 0.7);
    }

    @Override
    default void close() throws Exception {
    }
}
