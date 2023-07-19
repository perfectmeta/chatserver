package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.ai.embeddings.Embedding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VolatileMemoryStoreTest {


    @Test
    void simpleOperatorTest() {
        VolatileMemoryStore store = new VolatileMemoryStore();
        assertFalse(store.doesCollectionExist("TestCollection"));
        store.createCollection("TestCollection");
        assertTrue(store.doesCollectionExist("TestCollection"));
        var collections = store.getCollections();
        assertEquals(collections.size(), 1);
        assertEquals(collections.get(0), "TestCollection");
        store.deleteCollection("TestCollection");
        assertFalse(store.doesCollectionExist("TestCollection"));
        collections = store.getCollections();
        assertEquals(collections.size(), 0);
        // store.upsert("UpsertCollection", new MemoryRecord(null, Embedding.EMPTY, "key", null));
    }

    @Test
    void getNearestMatches() {
        VolatileMemoryStore store = new VolatileMemoryStore();
        store.createCollection("match");
        store.upsert("match", MemoryRecord.ofLocal("info1",
                "simple info 1",
                "default description",
                "metadata",
                new Embedding(new float[]{0.1f, 0.1f, 0.1f}), null, null
        ));
        store.upsert("match", MemoryRecord.ofLocal("info2",
                "simple info 2",
                "default descriptionr2",
                "metadata",
                new Embedding(new float[]{0.2f, 0.1f, 0.1f}), null, null
        ));

        store.upsert("match", MemoryRecord.ofLocal("info3",
                "simple info 3",
                "default description3",
                "metadata",
                new Embedding(new float[]{0.1f, 0.1f, 0.4f}), null, null
        ));

        store.upsert("match", MemoryRecord.ofLocal("info4",
                "simple info 4",
                "default description4",
                "metadata",
                new Embedding(new float[]{0.1f, 0.3f, 0.1f}), null, null
        ));

        var matchResult = store.getNearestMatches("match", new Embedding(new float[]{0.1f, 0.1f, 0.1f}), 3, 0.1);
        assertEquals(matchResult.size(), 3);
        for (var r : matchResult) {
            var vector = r.record().embedding().vector();
            System.out.printf("confidence: %f [%f, %f, %f]%n", r.confidence(), vector[0], vector[1], vector[2]);
        }
    }
}