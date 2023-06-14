package chatserver.logic.internal;

import com.google.common.base.Strings;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

@Component
public class SummaryMemory {
    private static final Logger logger = Logger.getLogger(SummaryMemory.class.getName());

    public String run(List<String> message, String prevSummary) {
        return run(message, prevSummary, 0L);
    }

    public String run(List<String> message, String prevSummary, long messageId) {
        // User user = AuthTokenInterceptor.USER.get();
        OpenAiService openAiService = makeOpenAiService();
        var prompt = makePrompt(message, prevSummary);
        CompletionRequest completeRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .stream(true)
                .build();

        var summary = new StringBuilder();
        openAiService.streamCompletion(completeRequest)
                .doOnError((e)->{})
                .blockingForEach(chunk->{
                    if (chunk.getChoices().size() > 0) {
                        var choice = chunk.getChoices().get(0);
                        summary.append(choice.getText());
                    }
                });

        logger.info("New Summary: ==================================================");
        logger.info(summary.toString());
        return summary.toString();
    }

    private String makePrompt(List<String> message, String prevSummary) {
        var prompt = new StringBuilder();
        if (!Strings.isNullOrEmpty(prevSummary)) {
            prompt.append("下面我将提供一段摘要和一段对话，请你为我将他们总结成一段新的摘要。\n");
            prompt.append("摘要内容:").append(prevSummary).append("\n");
        } else {
            prompt.append("请为我生成以下对话的摘要");
        }
        prompt.append("对话内容:\n");
        message.forEach(c->{
            prompt.append(c);
            if (!c.endsWith("\n")) {
                prompt.append("\n");
            }
        });
        prompt.append("新摘要:\n");
        logger.info("Prompt: ==================================================");
        logger.info(prompt.toString());
        return prompt.toString();
    }
}
