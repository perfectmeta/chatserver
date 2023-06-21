package chatserver.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PromptTest {
    private static final Logger logger = Logger.getLogger(PromptTest.class.getName());

    private Prompt prompt;
    private Map<String, String> variables;

    @BeforeAll
    public void init() throws Exception {
        constructPrompt();
        constructVariables();
    }

    @Test
    public void getMessageTest() {
        var filledMessage = prompt.getMessage(variables);
        for (var v : filledMessage) {
            logger.info("Got message " + v.content());
        }
    }

    private void constructPrompt() {
        var promptBuilder = Prompt.newBuilder();
        promptBuilder.addMessage(Prompt.PromptRole.System, "你将会扮演一个英语老师，你的名字是{{bot}}");
        promptBuilder.addMessage(Prompt.PromptRole.User, "你好{{bot}}, 我是新来的转校生{{user}}");
        promptBuilder.addMessage(Prompt.PromptRole.Assistant, "ok, 那么我们开始上课");
        prompt = promptBuilder.build();
    }

    private void constructVariables() {
        var variables = new HashMap<String, String>();
        variables.put("bot", "aya");
        variables.put("user", "earneet");
        this.variables = variables;
    }
}
