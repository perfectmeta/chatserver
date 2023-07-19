package com.perfectword.semantic_kernel.memory;

public class MemoryException extends RuntimeException {
    private final ErrorCodes errorCode;
    private String message;

    public MemoryException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public MemoryException(ErrorCodes errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public MemoryException(ErrorCodes errorCode, String message, Exception innerException) {
        super(innerException);
        this.errorCode = errorCode;
        this.message = message;
    }


    public String msg() {
        if (message == null) {
            return errorCode.getMessage();
        }
        return "%s:%s".formatted(errorCode.getMessage(), message);
    }

    public enum ErrorCodes {
        UnknownError(-1, "unknown error"),

        FailedToCreateCollection(0, "failed to create collection"),

        FailedToDeleteCollection(1, "failed to delete collection"),

        UnableToDeserializeMetadata(2, "Unable to deserialize metadata"),

        AttemptedToAccessNonexistentCollection(3, "Attempted to access nonexistent collection");

        ErrorCodes(int value, String message) {
            this.value = value;
            this.message = message;
        }

        private final int value;
        private final String message;

        public String getMessage() {
            return message;
        }

        public int getValue() {
            return value;
        }
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
