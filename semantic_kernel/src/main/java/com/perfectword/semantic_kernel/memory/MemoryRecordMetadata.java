package com.perfectword.semantic_kernel.memory;

public class MemoryRecordMetadata{
    private String id;
    private boolean isReference;
    private String externalSourceName;
    private String description;
    private String text;
    private String additionalMetadata;

    public MemoryRecordMetadata(String id, boolean isReference, String externalSourceName, String description, String text, String additionalMetadata) {
        this.id = id;
        this.isReference = isReference;
        this.externalSourceName = externalSourceName;
        this.description = description;
        this.text = text;
        this.additionalMetadata = additionalMetadata;
    }

    public String getId() {
        return id;
    }

    public boolean isReference() {
        return isReference;
    }

    public String getExternalSourceName() {
        return externalSourceName;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public String getAdditionalMetadata() {
        return additionalMetadata;
    }
}
