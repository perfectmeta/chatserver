package com.perfectword.semantic_kernel.template_engine.blocks;

public enum BlockTypes {
    Unidefined(0),
    Text(1),
    Code(2),
    Variable(3),
    Value(4),
    FucntionId(5);

    BlockTypes(int value) {
        this.intValue = value;
    }

    private final int intValue;
}
