package com.perfectword.semantic_kernel;

import java.lang.reflect.Method;
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

    public static void validSkillName(String skillName) {
        // todo implement function
    }

    public static void ValidFunctionName(String functionName) {
        // todo implement function
    }

    public static void NotNull(Method methodSignature) {
        Objects.requireNonNull(methodSignature);
    }
}
