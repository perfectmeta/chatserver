package chatserver.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prompt {
    public enum PromptRole {
        System("S:"),
        Assistant("A:"),
        User("U:");

        private final String prefix;
        PromptRole(String v) {
            prefix = v;
        }
        public String prefix() {
            return prefix;
        }
    }

    public class PromptBuilder {
        public void addMessage(PromptRole role, String content) {
            Prompt.this.messageList.add(new PromptMessage(role, content));
        }

        public Prompt build() {
            return Prompt.this;
        }
    }

    public record PromptMessage(PromptRole role, String content) {}

    private final List<PromptMessage> messageList;

    private Prompt() {
        messageList = new ArrayList<>();
    }

    public List<PromptMessage> getMessage(Map<String, String> variables) {
        @SuppressWarnings("all")
        var regex = "\\{\\{(\\w+)\\}\\}";
        var pattern = Pattern.compile(regex);
        var result = new ArrayList<PromptMessage>();
        for (PromptMessage(PromptRole role, String content) : messageList) {
            Matcher matcher = pattern.matcher(content);
            var _content = content;
            // fixme improve performance using string builder
            while (matcher.find()) {
                String variableName = matcher.group(1);
                _content = _content.replaceAll("\\{\\{"+ variableName + "\\}\\}", variables.get(variableName));
            }
            result.add(new PromptMessage(role, _content));
        }
        return result;
    }

    public static PromptBuilder newBuilder() {
        return (new Prompt()).new PromptBuilder();
    }
}
