package com.perfectword.semantic_kernal.memory;

public class MemoryQueryResult {
    private MemoryRecordMetadata metadata;
    private double relevance;

    public MemoryRecordMetadata getMetadata() {
        return metadata;
    }

    public double getRelevance() {
        return relevance;
    }
}
