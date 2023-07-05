package com.perfectword.semantic_kernal.semantic_functions;

import java.util.ArrayList;
import java.util.List;

public class PromptTemplateConfig {

    public class CompletionConfig {
        public double temperature;
        public double topP;
        public double presencePenalty;
        public double frequencyPenalty;
        public int maxTokens = 256;
        public List<String> stopSequences = new ArrayList<>();
    }
    public class InputParameter {
        public String name = "";
        public String description = "";
        public String defaultValue = "";
    }

    public class InputConfig {
        public List<InputParameter> parameters = new ArrayList<>();
    }

    private int schema;
    private String type = "completion";
    private String description;
    private CompletionConfig completion = new CompletionConfig();
    private List<String> defaultServices = new ArrayList<String>();
    private InputConfig input = new InputConfig();

    public PromptTemplateConfig compact() {
        if (completion.stopSequences.size() != 0) {
            this.completion.stopSequences = null;
        }

        if (defaultServices.size() == 0) {
            this.defaultServices = null;
        }
        return this;
    }

    public int getSchema() {
        return schema;
    }

    public void setSchema(int schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompletionConfig getCompletion() {
        return completion;
    }

    public void setCompletion(CompletionConfig completion) {
        this.completion = completion;
    }

    public List<String> getDefaultServices() {
        return defaultServices;
    }

    public void setDefaultServices(List<String> defaultServices) {
        this.defaultServices = defaultServices;
    }

    public InputConfig getInput() {
        return input;
    }

    public void setInput(InputConfig input) {
        this.input = input;
    }
}
