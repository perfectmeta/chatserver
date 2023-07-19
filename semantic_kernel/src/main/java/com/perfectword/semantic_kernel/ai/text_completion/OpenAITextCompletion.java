package com.perfectword.semantic_kernel.ai.text_completion;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

public class OpenAITextCompletion implements ITextCompletion {
    private final OpenAiService service;
    private final String model;

    public OpenAITextCompletion(OpenAiService service, String model) {
        this.service = service;
        this.model = model;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String getCompletion(String text, CompleteRequestSettings requestSettings) {
        var c = requestSettings.config();
        CompletionRequest req = new CompletionRequest();
        req.setModel(model);
        req.setTemperature(c.temperature());
        req.setTopP(c.topP());
        req.setFrequencyPenalty(c.frequencyPenalty());
        req.setPresencePenalty(c.presencePenalty());
        req.setMaxTokens(c.maxTokens());
        req.setStop(c.stopSequences());
        req.setPrompt(text);

        try {
            CompletionResult res = service.createCompletion(req);
            if (res == null) {
                throw new KernelException(ErrorCodes.AIServiceError, "Text completion null response");
            }

            if (res.getChoices().size() == 0) {
                throw new KernelException(ErrorCodes.AIInvalidResponseContent, "Text completion not found");
            }

            return res.getChoices().get(0).getText();

        } catch (Exception e) {
            throw new KernelException(ErrorCodes.AIServiceError, e.getMessage(), e);
        }
    }

}
