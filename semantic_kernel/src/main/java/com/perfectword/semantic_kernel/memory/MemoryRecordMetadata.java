package com.perfectword.semantic_kernel.memory;


public record MemoryRecordMetadata(
        String id,
        boolean isReference,
        String externalSourceName,
        String description,
        String text,
        String additionalMetadata) {
}

