package com.perfectword.semantic_kernel.ai.text_completion;

import java.util.List;

public interface IChatCompletion extends ITextCompletion {

    @Override
    default String getCompletion(
            String text,
            CompleteRequestSettings requestSettings
    ) {
        ChatHistory chat = new ChatHistory(List.of(new ChatHistory.ChatMessage(ChatHistory.Role.System, text)));
        return getChatCompletion(chat, requestSettings);
    }

    String getChatCompletion(
            ChatHistory chat,
            CompleteRequestSettings requestSettings
    );

    IChatCompletionStreamingResult getChatStreamingCompletion(
            ChatHistory chat,
            CompleteRequestSettings requestSettings
    );
}
