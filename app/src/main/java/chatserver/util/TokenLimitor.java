package chatserver.util;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenLimitor {

    private static final String SYSTEM = ChatMessageRole.SYSTEM.value();

    public static int limit(List<ChatMessage> messages, int limit, int keep) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);

        var tokenCnt = 0;
        ChatMessage system = null;
        var prompts = new ArrayList<ChatMessage>();
        var reverseResult = new ArrayList<ChatMessage>();
        var keepResult = new ArrayList<ChatMessage>();

        for (int i = 0; i < Math.min(messages.size(), keep) ; i++) {
            var msg = messages.get(i);
            tokenCnt += enc.encode(msg.getContent()).size();
            keepResult.add(msg);
        }

        var remainMessages = messages.subList(Math.min(keep, messages.size()), messages.size());
        for (int i = remainMessages.size()-1; i >= 0; i--) {
            var msg = remainMessages.get(i);
            var coders = enc.encode(msg.getContent());
            if (tokenCnt + coders.size() > limit) {
                break;
            }
            tokenCnt += coders.size();
            reverseResult.add(msg);
        }

        messages.clear();
        messages.addAll(keepResult);
        Collections.reverse(reverseResult);
        messages.addAll(reverseResult);
        return tokenCnt;
    }
}
