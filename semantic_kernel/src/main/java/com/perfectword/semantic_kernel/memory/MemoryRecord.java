package com.perfectword.semantic_kernel.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.time.OffsetDateTime;

public class MemoryRecord extends DataEntryBase{
    private final Embedding embedding;
    private final MemoryRecordMetadata metadata;

    public MemoryRecord(MemoryRecordMetadata metadata,
                        Embedding embedding,
                        String key,
                        OffsetDateTime timestamp) {
        super(key, timestamp);
        this.embedding = embedding;
        this.metadata = metadata;
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
                new MemoryRecordMetadata(externalId,
                        true,
                        sourceName,
                        description,
                        "",
                        additionalMetadata),
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
                        id,
                        false,
                        "",
                        description == null ? "" : description,
                        text,
                        additionalMetadata == null ? "" : additionalMetadata
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
        var objectMapper = new ObjectMapper();
        MemoryRecordMetadata metadata;
        try {
            metadata = objectMapper.readValue(json, MemoryRecordMetadata.class);
        } catch (JsonProcessingException e) {
            throw new MemoryException(MemoryException.ErrorCodes.UnableToDeserializeMetadata,
                    "Unable to create memory record from serialized metadata ", e);
        }
        return new MemoryRecord(metadata, embedding != null ? embedding: Embedding.EMPTY, key, timestamp);
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
