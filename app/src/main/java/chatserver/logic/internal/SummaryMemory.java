package chatserver.logic.internal;

import com.google.common.base.Strings;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

@Component
public class SummaryMemory {
    private static final Logger logger = Logger.getLogger(SummaryMemory.class.getName());
    public String run(List<String> message, String prevSummary) {
        // User user = AuthTokenInterceptor.USER.get();
        OpenAiService openAiService = makeOpenAiService();
        var prompt = new StringBuilder();
        if (!Strings.isNullOrEmpty(prevSummary)) {
            prompt.append("下面我将提供一段摘要和一段对话，请你为我将他们总结成一段新的摘要。\n");
            prompt.append("摘要内容:").append(prevSummary).append("\n");
        } else {
            prompt.append("请为我生成以下对话的摘要");
        }
        prompt.append("对话内容:\n");
        message.forEach(prompt::append);
        prompt.append("新摘要:\n");
        logger.info("Prompt: ==================================================");
        logger.info(prompt.toString());
        CompletionRequest completeRequest = CompletionRequest.builder()
                .prompt(prompt.toString())
                .model("text-davinci-003")
                //.maxTokens(2048)
                .stream(false)
                .build();

        var summary = new StringBuilder();
        openAiService.createCompletion(completeRequest).getChoices().forEach(x->{
            summary.append(x.getText());
            logger.info(x.getText());
        });
        return summary.toString();
    }
}
