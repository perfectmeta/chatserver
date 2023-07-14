package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.template_engine.blocks.Block;
import com.perfectword.semantic_kernel.template_engine.blocks.BlockTypes;
import com.perfectword.semantic_kernel.template_engine.blocks.TextBlock;
import com.perfectword.semantic_kernel.template_engine.blocks.VarBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromptTemplateEngine implements IPromptTemplateEngine {
    private static final int MIN_CODE_BLOCK_SIZE = 5;
    private static final char BLOCK_STARTER = '{';
    private static final char BLOCK_ENDER = '}';
    private static final char ESCAPE_CHAR = '\\';
    private static final char DOUBLE_QUOTE = '\"';
    private static final char SINGLE_QUOTE = '\'';
    private static final char VAR_PREFIX = '$';
    private static final char SPACE = ' ';
    private static final char TAB = '\t';
    private static final char NEW_LINE = '\n';
    private static final char CARRIAGE_RETURN = '\r';

    @Override
    public List<Block> extractBlocks(String templateText, boolean validate) {
        if (templateText == null || templateText.isBlank()) {
            return new ArrayList<>();
        }

        if (templateText.length() < MIN_CODE_BLOCK_SIZE) {
            return List.of(new TextBlock(templateText));
        }

        List<Block> blocks = new ArrayList<>();
        int endOfLastBlock = 0;

        int blockStartPos = 0;
        boolean blockStartFound = false;

        boolean insideTextValue = false;
        char textValueDelimiter = '\0';

        boolean skipNextChar = false;
        char nextChar = templateText.charAt(0);
        for (int nextCursor = 1; nextCursor < templateText.length(); nextCursor++) {
            int curCharPos = nextCursor - 1;
            int cursor = nextCursor;
            char curChar = nextChar;
            nextChar = templateText.charAt(nextCursor);

            if (skipNextChar) {
                skipNextChar = false;
                continue;
            }

            if (!insideTextValue && curChar == BLOCK_STARTER && nextChar == BLOCK_STARTER) {
                blockStartPos = curCharPos;
                blockStartFound = true;
            }

            if (blockStartFound) {
                if (insideTextValue) {
                    if (curChar == ESCAPE_CHAR && canBeEscape(nextChar)) {
                        skipNextChar = true;
                        continue;
                    }

                    if (curChar == textValueDelimiter) {
                        insideTextValue = false;
                    }
                } else {
                    if (curChar == DOUBLE_QUOTE || curChar == SINGLE_QUOTE) {
                        insideTextValue = true;
                        textValueDelimiter = curChar;
                    } else if (curChar == BLOCK_ENDER && nextChar == BLOCK_ENDER) {
                        if (blockStartPos > endOfLastBlock) {
                            blocks.add(new TextBlock(templateText.substring(endOfLastBlock, blockStartPos)));
                        }

                        var contentWithDelimiters = templateText.substring(blockStartPos, cursor + 1);
                        var contentWithoutDelimiters = contentWithDelimiters
                                .substring(2, contentWithDelimiters.length() - 2).trim();
                        if (contentWithoutDelimiters.length() == 0) {
                            // like {{ }} we consider it's a text block
                            blocks.add(new TextBlock(contentWithDelimiters));
                        } else {
                            // this branch processl {{ value }} case, but we don't process {{ target.value }} now
                            // List<Block> codeBlocks = extractBlocks(contentWithoutDelimiters);
                            blocks.add(new VarBlock(contentWithoutDelimiters));
                        }
                        endOfLastBlock = cursor + 1;
                        blockStartFound = false;
                    }
                }
            }
        }
        if (endOfLastBlock < templateText.length()) {
            blocks.add(new TextBlock(templateText.substring(endOfLastBlock)));
        }
        return blocks;
    }

    @Override
    public List<Block> renderVariables(List<Block> blocks, ContextVariables variables) {
        return blocks.stream()
                .map(x->x.getType() == BlockTypes.Variable ? new TextBlock(((VarBlock)x).render(variables)): x)
                .toList();
    }

    @Override
    public String render(List<Block> blocks, SKContext context) {
        List<Block> renderedBlocks = renderVariables(blocks, context.getVariables());
        return renderedBlocks.stream().map(Block::getContent).collect(Collectors.joining());
    }

    public String render(String templateText, SKContext context) {
        List<Block> blocks = extractBlocks(templateText, false);
        return render(blocks, context);
    }

    private boolean canBeEscape(char c) {
        return c == DOUBLE_QUOTE || c == SINGLE_QUOTE || c == ESCAPE_CHAR;
    }
}
