package com.perfectword.semantic_kernal.ai.text_completion;

import java.util.List;

public class TextCompletion implements ITextCompletion{
    @Override
    public List<ITextCompletionResult> getCompletions(String text, CompleteRequestSettings requestSettings) {
        return null;
    }

    @Override
    public ITextCompletionStreamingResult getStreamingCompletions(String text, CompleteRequestSettings requestSettings) {
        return null;
    }
}
