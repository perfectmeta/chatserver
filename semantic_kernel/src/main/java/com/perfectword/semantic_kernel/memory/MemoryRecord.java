package com.perfectword.semantic_kernel.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.time.OffsetDateTime;

public class MemoryRecord extends DataEntryBase{
    private Embedding embedding;
    private MemoryRecordMetadata metadata;

    public MemoryRecord(MemoryRecordMetadata metadata,
                        Embedding embedding,
                        String key,
                        OffsetDateTime timestamp) {
        super(key, timestamp);
    }

    public static MemoryRecord referenceRecord(
            String externalId,
            String sourceName,
            String description,
            Embedding embedding,
            String additionalMetadata,
            String key,
            OffsetDateTime timestamp
    ) {
        return new MemoryRecord(
                new MemoryRecordMetadata(
                    // todo: fill this constructor
                        externalId
                ),
                embedding,
                key,
                timestamp
        );
    }


    public static MemoryRecord localRecord(
            String id,
            String text,
            String description,
            Embedding embedding,
            String additionalMetadata,
            String key,
            OffsetDateTime timestamp
    ) {
        return new MemoryRecord(
                new MemoryRecordMetadata(
                        // todo: fill this constructor
                        id
                ),
                embedding,
                key,
                timestamp
        );
    }

    public static MemoryRecord fromJsonMetadata(
            String json,
            Embedding embedding,
            String key,
            OffsetDateTime timestamp
    ) {
        // todo fill this constructor
        return null;
    }

    public static MemoryRecord fromMetadata(
            MemoryRecordMetadata metadata,
            Embedding embedding,
            String key,
            OffsetDateTime timestamp
    ) {
        return new MemoryRecord(metadata, embedding, key, timestamp);
    }

    public String serializeMetadata() {
        var mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Embedding getEmbedding() {
        return embedding;
    }

    public MemoryRecordMetadata getMetadata() {
        return metadata;
    }
}
