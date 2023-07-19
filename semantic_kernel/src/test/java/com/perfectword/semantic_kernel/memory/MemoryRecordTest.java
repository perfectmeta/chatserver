package com.perfectword.semantic_kernel.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.perfectword.semantic_kernel.ai.embeddings.Embedding;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemoryRecordTest {
    @Test
    @Order(0)
    void jsonTest() throws JsonProcessingException {

        var metaJson = """
                {
                    "is_reference": false,
                    "id": "Id",
                    "text": "text",
                    "description": "description",
                    "external_source_name": "externalSourceName",
                    "additional_metadata": "value"
                }
                        """;
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        MemoryRecordMetadata metadata = mapper.readValue(metaJson, MemoryRecordMetadata.class);
        assertNotNull(metadata);
    }

    @Test
    @Order(1)
    void fromJsonMetadata() {
        var metaJson = """
                {
                    "is_reference": false,
                    "id": "Id",
                    "text": "text",
                    "description": "description",
                    "external_source_name": "externalSourceName",
                    "additional_metadata": "value"
                }
                        """;
        var memoryRecord = MemoryRecord.fromJsonMetadata(metaJson, Embedding.EMPTY, "key", OffsetDateTime.now());
        assertNotNull(memoryRecord);
        assertEquals(memoryRecord.getMetadata().text(), "text");
        assertEquals(memoryRecord.getMetadata().description(), "description");
    }

    @Test
    @Order(2)
    void serializeMetadata() {
        MemoryRecordMetadata metadata = new MemoryRecordMetadata("id",
                false,
                "externalSourceName",
                "description",
                "text",
                "additional_metadata"
                );
        var memoryRecord = MemoryRecord.fromMetadata(metadata, Embedding.EMPTY, "key", OffsetDateTime.now());
        var metaJson = memoryRecord.serializeMetadata();
        System.out.println(metaJson);

        var memoryRecordCopy = MemoryRecord.fromJsonMetadata(metaJson, Embedding.EMPTY, "key", OffsetDateTime.now());

        assertNotNull(memoryRecordCopy);
        assertNotNull(memoryRecord.getMetadata());
        assertEquals(memoryRecord.getMetadata().text(), memoryRecordCopy.getMetadata().text());
    }
}