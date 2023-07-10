package com.perfectword.semantic_kernal.ai.text_completion;

import com.perfectword.semantic_kernal.semantic_functions.CompletionConfig;

import java.util.List;

public class CompleteRequestSettings {
    private double temperature;
    private double topP;
    private double presencePenalty;
    private List<String> stopSequences;
    private double frequencyPenalty;
    private double maxTokens;
    private int resultPerPrompt;

    public CompleteRequestSettings(double temperature,
                                   double topP,
                                   double presencePenalty,
                                   List<String> stopSequences,
                                   double frequencyPenalty,
                                   double maxTokens) {
        this.temperature = temperature;
        this.topP = topP;
        this.presencePenalty = presencePenalty;
        this.stopSequences = stopSequences;
        this.frequencyPenalty = frequencyPenalty;
        this.maxTokens = maxTokens;
    }

    public static CompleteRequestSettings fromPromptTemplateConfig(CompletionConfig config) {
        return new CompleteRequestSettings(
                config.temperature,
                config.topP,
                config.presencePenalty,
                config.stopSequences,
                config.frequencyPenalty,
                config.maxTokens
        );
    }

    public int getResultPerPrompt() {
        return resultPerPrompt;
    }

    public void setResultPerPrompt(int resultPerPrompt) {
        this.resultPerPrompt = resultPerPrompt;
    }

    public List<String> getStopSequences() {
        return stopSequences;
    }

    public void setStopSequences(List<String> stopSequences) {
        this.stopSequences = stopSequences;
    }

    public double getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(double maxTokens) {
        this.maxTokens = maxTokens;
    }

    public double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTopP() {
        return topP;
    }

    public void setTopP(double topP) {
        this.topP = topP;
    }

    public double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(double presencePen) {
        this.presencePenalty = presencePen;
    }

}
