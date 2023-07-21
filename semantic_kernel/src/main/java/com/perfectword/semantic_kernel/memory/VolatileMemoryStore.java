package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;
import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.time.OffsetDateTime;
import java.util.*;

public class VolatileMemoryStore implements IMemoryStore {

    Map<String, Map<String, MemoryRecord>> store = new HashMap<>();

    @Override
    public void createCollection(String collectionName) {
        if (store.containsKey(collectionName)) {
            throw new KernelException(ErrorCodes.MemoryFailedToCreateCollection,
                    "Could not create collection %s".formatted(collectionName));
        }
        store.putIfAbsent(collectionName, new HashMap<>());
    }

    @Override
    public List<String> getCollections() {
        return store.keySet().stream().toList();
    }

    @Override
    public boolean doesCollectionExist(String collectionName) {
        return store.containsKey(collectionName);
    }

    @Override
    public void deleteCollection(String collectionName) {
        store.remove(collectionName);
    }

    @Override
    public MemoryRecord upsert(String collectionName, MemoryRecord record) {
        Verify.notNull(record);

        var collection = store.get(collectionName);
        if (collection == null) {
            throw new KernelException(ErrorCodes.MemoryAttemptedToAccessNonexistentCollection,
                    "%s not exist".formatted(collectionName));
        }

        if (record.key() == null) {
            record = record.setKeyAndTimestamp(record.id(), OffsetDateTime.now());
        }

        collection.put(record.key(), record);
        return record;
    }

    @Override
    public List<MemoryRecord> upsertBatch(String collectionName, List<MemoryRecord> records) {
        return records.stream().map(r -> upsert(collectionName, r)).toList();
    }

    @Override
    public List<MemoryRecord> getBatch(String collectionName, List<String> keys, boolean withEmbeddings) {
        var result = new ArrayList<MemoryRecord>();
        store.computeIfPresent(collectionName, (k, v) -> {
            keys.forEach(key -> v.computeIfPresent(key, (_k, _v) -> {
                result.add(_v);
                return _v;
            }));
            return v;
        });
        return result;
    }

    @Override
    public void remove(String collectionName, String key) {
        store.computeIfPresent(collectionName, (k, v) -> {
            v.remove(key);
            return v;
        });
    }

    @Override
    public void removeBatch(String collectionName, List<String> keys) {
        store.computeIfPresent(collectionName, (k, v) -> {
            keys.forEach(v::remove);
            return v;
        });
    }

    @Override
    public List<MemoryQueryResult> getNearestMatches(String collectionName,
                                                     Embedding embedding,
                                                     int limit,
                                                     double minRelevanceScore) {
        if (limit <= 0) {
            return List.of();
        }
        var collection = store.get(collectionName);
        if (collection == null) {
            return List.of();
        }

        PriorityQueue<MemoryQueryResult> priorityQueue = new PriorityQueue<>(limit, Comparator.comparingDouble(MemoryQueryResult::confidence).reversed());
        for (MemoryRecord mr : collection.values()) {
            if (mr == null) continue;
            double similarity = mr.embedding().cosineSimilarity(embedding);
            if (similarity >= minRelevanceScore) {
                priorityQueue.add(new MemoryQueryResult(mr, similarity));
            }
        }
        return priorityQueue.stream().limit(limit).toList();
    }
}
