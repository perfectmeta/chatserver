package com.perfectword.semantic_kernel.ai.text_completion;

import com.perfectword.semantic_kernel.ai.AIException;
import com.perfectword.semantic_kernel.ai.AIException.ErrorCodes;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Flowable;

public class OpenAIChatCompletion implements IChatCompletion {
    private final OpenAiService service;
    private final String model;

    public OpenAIChatCompletion(OpenAiService service, String model) {
        this.service = service;
        this.model = model;
    }

    @Override
    public String getChatCompletion(ChatHistory chat, CompleteRequestSettings requestSettings) {
        ChatCompletionRequest req = createRequest(chat, requestSettings);
        try {
            ChatCompletionResult res = service.createChatCompletion(req);
            if (res == null) {
                throw new AIException(ErrorCodes.ServiceError, "Chat completion null response");
            }

            if (res.getChoices().size() == 0) {
                throw new AIException(ErrorCodes.InvalidResponseContent, "Chat completion not found");
            }

            return res.getChoices().get(0).getMessage().getContent();

        } catch (Exception e) {
            throw new AIException(ErrorCodes.ServiceError, e);
        }
    }

    @Override
    public IChatCompletionStreamingResult getChatStreamingCompletion(ChatHistory chat, CompleteRequestSettings requestSettings) {
        ChatCompletionRequest req = createRequest(chat, requestSettings);
        Flowable<ChatCompletionChunk> flow = service.streamChatCompletion(req);
        return new OpenAIChatCompletionStreamingResult(flow);
    }

    private ChatCompletionRequest createRequest(ChatHistory chat, CompleteRequestSettings requestSettings) {
        var c = requestSettings.config();
        ChatCompletionRequest req = new ChatCompletionRequest();
        req.setModel(model);
        req.setTemperature(c.temperature());
        req.setTopP(c.topP());
        req.setFrequencyPenalty(c.frequencyPenalty());
        req.setPresencePenalty(c.presencePenalty());
        req.setMaxTokens(c.maxTokens());
        req.setStop(c.stopSequences());
        req.setMessages(chat.history().stream().map(m -> new ChatMessage(m.role().toString(), m.content())).toList());
        return req;
    }
}
