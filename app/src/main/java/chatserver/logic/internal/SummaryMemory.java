package chatserver.logic.internal;

import chatserver.entity.User;
import chatserver.logic.AuthTokenInterceptor;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.util.List;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

@Component
public class SummaryMemory {
    public void run(List<String> message) {
        User user = AuthTokenInterceptor.USER.get();
        OpenAiService openAiService = makeOpenAiService();
        // openAiService.createCompletion()
    }
}
