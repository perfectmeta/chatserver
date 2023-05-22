package chatserver.logic.internal;

import chatserver.dao.User;
import chatserver.logic.AuthTokenInterceptor;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

import static chatserver.third.openai.OpenAi.makeOpenAiService;

public class Summary {
    public void run(List<String> message) {
        User user = AuthTokenInterceptor.USER.get();
        OpenAiService openAiService = makeOpenAiService();
        // openAiService.createCompletion()
    }
}
