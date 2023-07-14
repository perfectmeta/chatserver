package com.perfectword.semantic_kernel.template_engine.blocks;

public class TextBlock extends Block{
    public TextBlock(String content) {
        super(content);
    }

    @Override
    public ValidInfo isValid() {
        return null;
    }

    @Override
    public BlockTypes getType() {
        return BlockTypes.Text;
    }
}
