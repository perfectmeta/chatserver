package com.perfectword.semantic_kernel.ai.text_completion;

import java.util.List;

public record ChatHistory(List<ChatMessage> history) {


    public record ChatMessage(Role role, String content) {
    }

    public enum Role {
        System,
        User,
        Assistant
    }
}