package com.perfectword.semantic_kernel.memory;

public record MemoryQueryResult(String id, String description, String text, double relevance) {
}
