package com.perfectword.semantic_kernel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Verify {

    public static void validFunctionName(String functionName) {
        Objects.requireNonNull(functionName);
    }

    public static void validFunctionParamName(String functionParamName) {
        Objects.requireNonNull(functionParamName);
    }

    public static void notNullOrWhiteSpace(String name) {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public static void validSkillName(String skillName) {
        notNullOrWhiteSpace(skillName);
    }

    public static void notNull(Object obj) {
        Objects.requireNonNull(obj);
    }

    public static void directoryExists(Path skillDir) {
        if (!Files.exists(skillDir)) {
            throw new IllegalArgumentException();
        }
    }
}
