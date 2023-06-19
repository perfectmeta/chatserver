package chatserver.util;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.ArrayList;
import java.util.List;

public class TokenLimitor {

    private static final String SYSTEM = ChatMessageRole.SYSTEM.value();

    public static void limit(List<ChatMessage> messages, int limit) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);

        var tokenCnt = 0;
        ChatMessage system = null;
        var result = new ArrayList<ChatMessage>();
        var systemOpt = messages.stream().filter(x->x.getRole().equals(SYSTEM)).findFirst();
        if (systemOpt.isPresent()) {
            system = systemOpt.get();
            tokenCnt += enc.encode(system.getContent()).size();
        }

        for (int i = messages.size() - 1; i >= 0; i--) {
            var chatMessage = messages.get(i);
            if (chatMessage.getRole().equals(SYSTEM)) {
                continue;
            }
            var coder = enc.encode(chatMessage.getContent());
            if (tokenCnt + coder.size() > limit) {
                break;
            }
            tokenCnt += coder.size();
            result.add(chatMessage);
        }

        messages.clear();
        if (system != null) {
            messages.add(system);
        }

        for (int i = result.size() - 1; i >= 0; i--) {
            messages.add(result.get(i));
        }
    }
}
