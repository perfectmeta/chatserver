package com.perfectword.semantic_kernal.ai;

import com.perfectword.semantic_kernal.KernelException;

public class AIException extends RuntimeException {

    public final ErrorCodes errorCodes;
    public final String detail;
    public final Exception innerException;
    public final String message;

    public AIException(ErrorCodes errorCodes) {
        this(errorCodes, null, null, null);
    }
    public AIException(ErrorCodes errorCode, String message) {
        this(errorCode, message, null, null);
    }

    public AIException(ErrorCodes error, String message, String detail) {
        this(error, message, detail, null);
    }

    public AIException(ErrorCodes errorCode, String message, Exception innerException) {
        this(errorCode, message, null, innerException);
    }

    public AIException(ErrorCodes errorCode, String message, String detail, Exception innerException) {
        this.errorCodes = errorCode;
        this.detail = detail;
        this.message = message;
        this.innerException = innerException;
    }

    private static String getDefaultMessage(ErrorCodes errorCodes, String message) {
        if (message != null) {
            return "%s: %s".formatted(errorCodes.message, message);
        }
        return errorCodes.message;
    }

    public enum ErrorCodes {

        UnknownError(-1, "Unknown error"),

        NoResponse(0, "No response"),

        AccessDenied(1, "Access denied"),

        InvalidRequest(2, "Invalid request"),

        InvalidResponseContent(3, "Invalid response content"),

        Throttling(4, "Throttling"),

        RequestTimeout(5, "Request timeout"),

        ServiceError(6, "Service error"),

        ModelNotAvailable(7, "Model not available"),

        InvalidConfiguration(8, "Invalid configuration"),

        FunctionTypeNotSupported(9, "Function type not supported");

        ErrorCodes(int eValue, String message) {
            this.intValue = eValue;
            this.message = message;
        }

        public final int intValue;
        public final String message;
    }
}
