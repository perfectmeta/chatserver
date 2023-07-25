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
        StringBuilder stringBuilder = new StringBuilder();
        Role role = null;
        for (var line : promptStr.lines().toList()) {
            Role tempRole = null;
            if (line.startsWith("S:")) {
                tempRole = Role.System;
            } else if (line.startsWith("U:")) {
                tempRole = Role.User;
            } else if (line.startsWith("A:")) {
                tempRole = Role.Assistant;
            }

            if (tempRole != null) {
                if (!stringBuilder.isEmpty()) {
                    list.add(new PromptMessage(role, stringBuilder.toString()));
                }
                role = tempRole;
                stringBuilder = new StringBuilder(line);
            }
            else
            {
                if (stringBuilder.isEmpty())
                    continue;
                if (stringBuilder.charAt(stringBuilder.length() - 1) == '\\') {
                    stringBuilder.setLength(stringBuilder.length()-1);
                    stringBuilder.append(line);
                }
                else if (stringBuilder.charAt(stringBuilder.length() - 1) == '$') {
                    stringBuilder.setLength(stringBuilder.length() - 1);
                    stringBuilder.append("\n").append(line);
                } else {
                    throw new IllegalStateException(line + " format error, must begin with a prefix S: or A: or U:");
                }
            }
        }
        if (!stringBuilder.isEmpty()) {
            list.add(new PromptMessage(role, stringBuilder.toString()));
        }

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
