package com.perfectword.semantic_kernel.memory;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class MemoryRecordMetadata{
    @JsonProperty
    private String id;
    @JsonProperty("is_reference")
    @JsonDeserialize
    private boolean isReference;
    @JsonProperty("external_source_name")
    @JsonDeserialize
    private String externalSourceName;
    @JsonProperty("description")
    @JsonDeserialize
    private String description;
    @JsonProperty("text")
    @JsonDeserialize
    private String text;
    @JsonProperty("additional_metadata")
    @JsonDeserialize
    private String additionalMetadata;

    public MemoryRecordMetadata() {}

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

    @JsonProperty("is_reference")
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
