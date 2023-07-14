package com.perfectword.semantic_kernel.template_engine.blocks;

import java.util.logging.Logger;

public abstract class Block {
    public BlockTypes getType() {
        return BlockTypes.Unidefined;
    }

    protected String _content;
    public String getContent() {
        return _content;
    }

    protected Block(String content) {
        _content = nullToEmpty(content);
    }

    private static String nullToEmpty(String content) {
        return content == null ? "" : content;
    }

    public record ValidInfo(boolean valid, String errorMessage){}

    public abstract ValidInfo isValid();
}
