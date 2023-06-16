package chatserver.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Prompt {
    public enum PromptRole {
        System,
        Assistant,
        User
    }

    public static class PromptBuilder {
        private List<PromptMessage> messages = new ArrayList<>();
        public void addMessage(PromptRole role, String content) {
            messages.add(new PromptMessage(role, content));
        }

        public Prompt build() {
            return new Prompt(messages);
        }
    }

    public record PromptMessage(PromptRole role, String content) {}

    private final List<PromptMessage> messageList;

    private Prompt(List<PromptMessage> messages) {
        messageList = Objects.requireNonNull(messages);
    }

    public List<PromptMessage> getMessage(Map<String, String> variables) {
        var regex = "\\{\\{.+\\}\\}";
        var pattern = Pattern.compile(regex);
        var result = new ArrayList<>();
        for (var msg : messageList) {
            var matcher = pattern.matcher(msg.content);
            if (matcher.matches()) {

            } else {
                result.add(new PromptMessage(msg.role, msg.content));
            }
        }
    }

    private String fillVariable(String template, Map<String, String> variables) {

    }

    public static PromptBuilder newBuilder() {
        return new PromptBuilder();
    }
}
