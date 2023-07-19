package com.perfectword.semantic_kernel.memory;

public record MemoryQueryResult(String id, String description, String text, double relevance) {

    public static MemoryQueryResult fromMemoryRecord(MemoryRecord record, float relevance) {
        return new MemoryQueryResult(record.getMetadata().id(),
                record.getMetadata().description(),
                record.getMetadata().text(),
                relevance);
    }
}
