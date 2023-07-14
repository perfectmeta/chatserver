package com.perfectword.semantic_kernel.template_engine.blocks;

import com.perfectword.semantic_kernel.orchestration.ContextVariables;

public class VarBlock extends Block {
    public VarBlock(String content) {
        super(content);
    }

    @Override
    public ValidInfo isValid() {
        return null;
    }

    @Override
    public BlockTypes getType() {
        return BlockTypes.Variable;
    }

    public String render(ContextVariables variables) {
        String value;
        if (_content.startsWith("$")) {
            value = variables.get(_content.substring(1).toLowerCase());
        } else {
            value = variables.get(_content);
        }
        return value != null ? value : _content;
    }
}
