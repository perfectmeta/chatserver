package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.time.OffsetDateTime;

public record MemoryRecord(
        String id,
        boolean isReference,
        String externalUrl,
        String externalSourceName,
        String description,
        String text,
        String additionalMetadata,
        Embedding embedding,
        String key,
        OffsetDateTime timestamp
) {

    public MemoryRecord setKeyAndTimestamp(String newKey, OffsetDateTime newTimestamp) {
        return new MemoryRecord(id, isReference, externalUrl, externalSourceName, description, text, additionalMetadata,
                embedding, newKey, newTimestamp);
    }

    public static MemoryRecord ofReference(
            String id,
            String externalUrl, // url to find original source
            String externalSourceName, // name of the external service
            String description,
            String additionalMetadata,
            Embedding embedding,
            String key,
            OffsetDateTime timestamp
    ) {
        return new MemoryRecord(
                id,
                true,
                externalUrl,
                externalSourceName,
                description,
                "",
                additionalMetadata,
                embedding,
                key,
                timestamp
        );
    }


    public static MemoryRecord ofLocal(
            String id,
            String text,
            String description,
            String additionalMetadata,
            Embedding embedding,
            String key,
            OffsetDateTime timestamp
    ) {
        return new MemoryRecord(
                id,
                false,
                "",
                "",
                description == null ? "" : description,
                text,
                additionalMetadata == null ? "" : additionalMetadata,
                embedding,
                key,
                timestamp
        );
    }
}
