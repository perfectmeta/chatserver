package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;

import java.util.ArrayList;
import java.util.List;

final class TemplateTokenizer {
    private static final int EmptyCodeBlockLength = 4;
    // A block shorter than 5 chars is either empty or invalid, e.g. "{{ }}" and "{{$}}"
    private static final int MinCodeBlockLength = EmptyCodeBlockLength + 1;

    private final CodeTokenizer codeTokenizer;

    public TemplateTokenizer() {
        codeTokenizer = new CodeTokenizer();
    }


    public List<Block> extractBlocks(String templateText) {

        if (templateText == null || templateText.isBlank()) {
            return new ArrayList<>();
        }

        if (templateText.length() < MinCodeBlockLength) {
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
            @SuppressWarnings("UnnecessaryLocalVariable")
            int cursor = nextCursor;
            char curChar = nextChar;
            nextChar = templateText.charAt(nextCursor);

            if (skipNextChar) {
                skipNextChar = false;
                continue;
            }

            if (!insideTextValue && curChar == Symbols.BLOCK_STARTER && nextChar == Symbols.BLOCK_STARTER) {
                blockStartPos = curCharPos;
                blockStartFound = true;
            }

            if (blockStartFound) {
                if (insideTextValue) {
                    if (curChar == Symbols.ESCAPE_CHAR && canBeEscape(nextChar)) {
                        skipNextChar = true;
                        continue;
                    }

                    if (curChar == textValueDelimiter) {
                        insideTextValue = false;
                    }
                } else {
                    // A value starts here
                    if (isQuote(curChar)) {
                        insideTextValue = true;
                        textValueDelimiter = curChar;

                    }
                    // If the block ends here
                    else if (curChar == Symbols.BLOCK_ENDER && nextChar == Symbols.BLOCK_ENDER) {

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
                            List<Block> codeBlocks = codeTokenizer.tokenize(contentWithoutDelimiters);


                            Block first = codeBlocks.get(0);
                            if (first instanceof VarBlock) {
                                if (codeBlocks.size() > 1) {
                                    throw new KernelException(ErrorCodes.TemplateSyntaxError,
                                            "Invalid token detected after the variable: %s".formatted(contentWithoutDelimiters));
                                }

                                blocks.add(first);
                            } else if (first instanceof ValBlock) {
                                if (codeBlocks.size() > 1) {
                                    throw new KernelException(ErrorCodes.TemplateSyntaxError,
                                            "Invalid token detected after the value: %s".formatted(contentWithoutDelimiters));
                                }

                                blocks.add(first);
                            } else if (first instanceof FuncBlock func) {

                                if (codeBlocks.size() > 2) {
                                    throw new KernelException(ErrorCodes.TemplateSyntaxError,
                                            "Functions support only one parameter: %s".formatted(contentWithoutDelimiters));
                                }
                                if (codeBlocks.size() == 1) {
                                    blocks.add(func);
                                } else {
                                    blocks.add(new FuncBlock(func.getSkillName(), func.getFunctionName(), codeBlocks.get(1)));
                                }

                            } else {
                                throw new KernelException(ErrorCodes.TemplateSyntaxError,
                                        "Code tokenizer returned an incorrect first token type %s".formatted(first.getClass().toString()));
                            }
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

    private static boolean isQuote(char c) {
        return c == Symbols.DOUBLE_QUOTE || c == Symbols.SINGLE_QUOTE;
    }


    private static boolean canBeEscape(char c) {
        return c == Symbols.DOUBLE_QUOTE || c == Symbols.SINGLE_QUOTE || c == Symbols.ESCAPE_CHAR;
    }


}
