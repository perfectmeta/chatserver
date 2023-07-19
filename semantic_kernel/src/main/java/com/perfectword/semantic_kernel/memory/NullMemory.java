package com.perfectword.semantic_kernel.memory;

import java.util.List;

public class NullMemory implements ISemanticTextMemory {

    private NullMemory() {
    }

    private static final NullMemory INSTANCE = new NullMemory();

    public static NullMemory getInstance() {
        return INSTANCE;
    }

    @Override
    public void saveInformation(String collection, String text, String id, String description, String additionalMetadata) {
    }

    @Override
    public MemoryQueryResult get(String collection, String key, boolean withEmbedding) {
        return null;
    }

    @Override
    public void remove(String collection, String key) {
    }

    @Override
    public List<MemoryQueryResult> search(String collection, String query, int limit, double minRelevanceScore, boolean withEmbeddings) {
        return List.of();
    }
}
