package com.perfectword.semantic_kernel.template_engine;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;

import java.util.ArrayList;
import java.util.List;

public class CodeTokenizer {
    private enum TokenTypes {
        None,
        Value,
        Variable,
        FunctionId
    }

    public List<Block> tokenize(String text) {
        // Remove spaces, which are ignored anyway
        text = text.trim();

        // Render NULL to ""
        if (text.isEmpty()) {
            return List.of();
        }

        // Track what type of token we're reading
        TokenTypes currentTokenType = TokenTypes.None;

        // Track the content of the current token
        var currentTokenContent = new StringBuilder();

        char textValueDelimiter = '\0';

        var blocks = new ArrayList<Block>();
        char nextChar = text.charAt(0);

        // Tokens must be separated by spaces, track their presence
        boolean spaceSeparatorFound = false;

        // 1 char only edge case
        if (text.length() == 1) {
            switch (nextChar) {
                case Symbols.VAR_PREFIX -> blocks.add(new VarBlock(text));
                case Symbols.DOUBLE_QUOTE, Symbols.SINGLE_QUOTE -> blocks.add(new ValBlock(text));
                default -> blocks.add(new FuncBlock(text));
            }

            return blocks;
        }

        boolean skipNextChar = false;
        for (int nextCharCursor = 1; nextCharCursor < text.length(); nextCharCursor++) {
            char currentChar = nextChar;
            nextChar = text.charAt(nextCharCursor);

            if (skipNextChar) {
                skipNextChar = false;
                continue;
            }

            // First char is easy
            if (nextCharCursor == 1) {
                if (isVarPrefix(currentChar)) {
                    currentTokenType = TokenTypes.Variable;
                } else if (isQuote(currentChar)) {
                    currentTokenType = TokenTypes.Value;
                    textValueDelimiter = currentChar;
                } else {
                    currentTokenType = TokenTypes.FunctionId;
                }

                currentTokenContent.append(currentChar);
                continue;
            }

            // While reading a values between quotes
            if (currentTokenType == TokenTypes.Value) {
                // If the current char is escaping the next special char:
                // - skip the current char (escape char)
                // - add the next (special char)
                // - jump to the one after (to handle "\\" properly)
                if (currentChar == Symbols.ESCAPE_CHAR && canBeEscaped(nextChar)) {
                    currentTokenContent.append(nextChar);
                    skipNextChar = true;
                    continue;
                }

                currentTokenContent.append(currentChar);

                // When we reach the end of the value
                if (currentChar == textValueDelimiter) {
                    blocks.add(new ValBlock(currentTokenContent.toString()));
                    currentTokenContent.setLength(0);
                    currentTokenType = TokenTypes.None;
                    spaceSeparatorFound = false;
                }

                continue;
            }

            // If we're not between quotes, a space signals the end of the current token
            // Note: there might be multiple consecutive spaces
            if (isBlankSpace(currentChar)) {
                if (currentTokenType == TokenTypes.Variable) {
                    blocks.add(new VarBlock(currentTokenContent.toString()));
                    currentTokenContent.setLength(0);
                } else if (currentTokenType == TokenTypes.FunctionId) {
                    blocks.add(new FuncBlock(currentTokenContent.toString()));
                    currentTokenContent.setLength(0);
                }

                spaceSeparatorFound = true;
                currentTokenType = TokenTypes.None;

                continue;
            }

            // If we're not inside a quoted value, and we're not processing a space
            currentTokenContent.append(currentChar);

            if (currentTokenType == TokenTypes.None) {
                if (!spaceSeparatorFound) {
                    throw new KernelException(ErrorCodes.TemplateSyntaxError,
                            "Tokens must be separated by one space least");
                }

                if (isQuote(currentChar)) {
                    // A quoted value starts here
                    currentTokenType = TokenTypes.Value;
                    textValueDelimiter = currentChar;
                } else if (isVarPrefix(currentChar)) {
                    // A variable starts here
                    currentTokenType = TokenTypes.Variable;
                } else {
                    // A function id starts here
                    currentTokenType = TokenTypes.FunctionId;
                }
            }
        }

        // Capture last token
        currentTokenContent.append(nextChar);
        switch (currentTokenType) {
            case Value -> blocks.add(new ValBlock(currentTokenContent.toString()));
            case Variable -> blocks.add(new VarBlock(currentTokenContent.toString()));
            case FunctionId -> blocks.add(new FuncBlock(currentTokenContent.toString()));
            case None -> throw new KernelException(ErrorCodes.TemplateSyntaxError,
                    "Tokens must be separated by one space least");
        }

        return blocks;
    }

    private static boolean isVarPrefix(char c) {
        return (c == Symbols.VAR_PREFIX);
    }

    private static boolean isBlankSpace(char c) {
        return c == Symbols.SPACE || c == Symbols.NEW_LINE || c == Symbols.CARRIAGE_RETURN || c == Symbols.TAB;
    }

    private static boolean isQuote(char c) {
        return c == Symbols.DOUBLE_QUOTE || c == Symbols.SINGLE_QUOTE;
    }

    private static boolean canBeEscaped(char c) {
        return c == Symbols.DOUBLE_QUOTE || c == Symbols.SINGLE_QUOTE || c == Symbols.ESCAPE_CHAR;
    }
}
