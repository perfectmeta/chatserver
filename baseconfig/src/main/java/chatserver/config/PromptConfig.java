package chatserver.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PromptConfig {
    public enum Role {
        System,
        Assistant,
        User
    }

    public record PromptMessage(Role role, String content) {
    }

    private List<PromptMessage> messageList;

    public static PromptConfig of(List<PromptMessage> messageList) {
        PromptConfig prompt = new PromptConfig();
        prompt.messageList = messageList;
        return prompt;
    }

    public static PromptConfig parse(String promptStr) {
        List<PromptMessage> list = new ArrayList<>();
        promptStr.lines().forEach(line -> {
            if (line.startsWith("S:")) {
                var content = line.substring(2);
                list.add(new PromptMessage(Role.System, content));

            } else if (line.startsWith("U:")) {
                var content = line.substring(2);
                list.add(new PromptMessage(Role.User, content));

            } else if (line.startsWith("A:")) {
                var content = line.substring(2);
                list.add(new PromptMessage(Role.Assistant, content));

            } else {
                throw new IllegalStateException(line + " format error, must begin with a prefix S: or A: or U:");
            }
        });
        return PromptConfig.of(list);
    }

    public List<PromptMessage> getMessage(Map<String, String> variables) {
        @SuppressWarnings("all")
        var regex = "\\{\\{(\\w+)\\}\\}";
        var pattern = Pattern.compile(regex);
        var result = new ArrayList<PromptMessage>();
        for (PromptMessage(Role role, String content) : messageList) {
            Matcher matcher = pattern.matcher(content);
            var _content = content;
            // fixme improve performance using string builder
            while (matcher.find()) {
                String variableName = matcher.group(1);
                if (variables.containsKey(variableName)) {
                    _content = _content.replaceAll("\\{\\{%s\\}\\}".formatted(variableName), variables.get(variableName));
                }
            }
            result.add(new PromptMessage(role, _content));
        }
        return result;
    }
}
