package com.perfectword.semantic_kernal.abstractions;

import java.util.Objects;

public class Verify {

    public static void validFunctionName(String functionName) {
        Objects.requireNonNull(functionName);
        // todo check FunctionName and throw certain exceptions
    }

    public static void validFunctionParamName(String functionParamName) {
        Objects.requireNonNull(functionParamName);
        // todo check FunctionName and throw certain exceptions
    }

    public static void notNullOrWhiteSpace(String name) {
        // todo implement function
    }
}
