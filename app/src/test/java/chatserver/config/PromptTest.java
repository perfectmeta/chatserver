package chatserver.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PromptTest {

    private PromptConfig promptConfig;
    private Map<String, String> variables;

    @BeforeAll
    public void init() {
        constructPrompt();
        constructVariables();
    }

    @Test
    public void getMessageTest() {
        var filledMessage = promptConfig.getMessage(variables);
        for (var v : filledMessage) {
            System.out.println("Got message " + v.content());
        }
    }

    private void constructPrompt() {
        List<PromptConfig.PromptMessage> list = new ArrayList<>();
        list.add(new PromptConfig.PromptMessage(PromptConfig.Role.System, "你将会扮演一个英语老师，你的名字是{{bot}}"));
        list.add(new PromptConfig.PromptMessage(PromptConfig.Role.User, "你好{{bot}}, 我是新来的转校生{{user}}"));
        list.add(new PromptConfig.PromptMessage(PromptConfig.Role.Assistant, "ok, 那么我们开始上课"));
        promptConfig = PromptConfig.of(list);
    }

    private void constructVariables() {
        var variables = new HashMap<String, String>();
        variables.put("bot", "aya");
        variables.put("user", "earneet");
        this.variables = variables;
    }
}
