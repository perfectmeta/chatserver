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

    public int schema = 1;
    public String type = "completion";
}
