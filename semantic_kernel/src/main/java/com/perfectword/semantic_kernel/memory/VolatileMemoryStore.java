package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.ai.embeddings.Embedding;

import java.util.*;

public class VolatileMemoryStore implements IMemoryStore {

    Map<String, Map<String, MemoryRecord>> store = new HashMap<>();

    @Override
    public void createCollection(String collectionName) {
        if (!store.containsKey(collectionName)) {
            throw new MemoryException(MemoryException.ErrorCodes.FailedToCreateCollection,
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
    public String upsert(String collectionName, MemoryRecord record) {
        Verify.notNull(record);
        if (!store.containsKey(collectionName)) {
            throw new MemoryException(MemoryException.ErrorCodes.AttemptedToAccessNonexistentCollection,
                    "Attempted to access a memory collection that does not exist: %s".formatted(collectionName));
        }
        var collection = store.get(collectionName);
        assert collection != null;
        collection.put(record.key, record);
        return record.key;
    }

    @Override
    public List<String> upsertBatch(String collectionName, List<MemoryRecord> records) {
        var result = new ArrayList<String>();
        records.forEach(r -> result.add(upsert(collectionName, r)));
        return result;
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
    public List<MatchResult> getNearestMatches(String collectionName,
                                               Embedding embedding,
                                               int limit,
                                               double minRelevanceScore,
                                               boolean withEmbeddings) {
        if (limit <= 0) {
            return List.of();
        }
        var collection = store.get(collectionName);
        if (collection == null) {
            return List.of();
        }

        PriorityQueue<MatchResult> priorityQueue = new PriorityQueue<>(limit, Comparator.comparingDouble(MatchResult::confidence).reversed());
        for (var mr : collection.values()) {
            if (mr == null) continue;
            double similarity = mr.getEmbedding().cosineSimilarity(embedding);
            if (similarity >= minRelevanceScore) {
                var entry = withEmbeddings ? mr : MemoryRecord.fromMetadata(mr.getMetadata(), Embedding.EMPTY, mr.getKey(), mr.getTimeStamp());
                priorityQueue.add(new MatchResult(entry, similarity));
            }
        }
        return priorityQueue.stream().toList();
    }
}
