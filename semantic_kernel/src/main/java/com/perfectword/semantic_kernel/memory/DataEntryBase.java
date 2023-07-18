package com.perfectword.semantic_kernel.memory;

import java.time.OffsetDateTime;

public class DataEntryBase {
    protected String key;
    protected OffsetDateTime timestamp;

    public DataEntryBase(String key, OffsetDateTime timestamp) {
        this.key = key;
        this.timestamp = timestamp;
    }

    public boolean hasTimestamp() {
        return timestamp != null;
    }

    public String getKey() {
        return key;
    }

    public OffsetDateTime getTimeStamp() {
        return timestamp;
    }
}
