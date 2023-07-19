package com.perfectword.semantic_kernel;

public class KernelException extends RuntimeException {
    private final ErrorCodes errorCode;

    public KernelException(ErrorCodes errorCode, String message) {
        this(errorCode, message, null);
    }

    public KernelException(ErrorCodes errorCode, String message, Throwable cause) {
        super(msg(errorCode, message), cause);
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    static String msg(ErrorCodes errorCode, String message) {
        String description = errorCode.message;
        if (message != null) {
            return "%s: %s".formatted(description, message);
        }
        return description;
    }


    public enum ErrorCodes {
        UnknownError(-1, "Unknown error"),
        InvalidFunctionDescription(0, "Invalid function description"),
        FunctionOverloadNotSupported(1, "Function overload not supported"),
        FunctionNotAvailable(2, "Function not available"),
        FunctionTypeNotSupported(3, "Function type not supported"),
        InvalidFunctionType(4, "Invalid function type"),
        InvalidServiceConfiguration(5, "Invalid service configuration"),
        ServiceNotFound(6, "Service not found"),
        SkillCollectionNotSet(7, "Skill collection not set"),
        FunctionInvokeError(8, "Function invocation error"),
        AmbiguousMatchFunctionType(9, "Ambiguous match function type"),
        TemplateSyntaxError(10, "template syntax error"),

        AIInvalidResponseContent(11, "Invalid response content"),
        AIServiceError(12, "Service error");



        ErrorCodes(int v, String message) {
            this.value = v;
            this.message = message;
        }

        final int value;
        final String message;
    }
}
