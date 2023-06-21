package chatserver.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String fillVariable(String content, Map<String, String> variables) {
        @SuppressWarnings("all")
        var regex = "\\{\\{(\\w+)\\}\\}";
        var pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        var _content = content;
        // fixme improve performance using string builder
        while (matcher.find()) {
            String variableName = matcher.group(1);
            _content = _content.replaceAll("\\{\\{"+ variableName + "\\}\\}", variables.get(variableName));
        }
        return _content;
    }
}
