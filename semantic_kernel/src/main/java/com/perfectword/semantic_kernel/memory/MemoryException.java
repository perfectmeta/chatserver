package com.perfectword.semantic_kernel.memory;

import com.perfectword.semantic_kernel.KernelException;

public class MemoryException extends RuntimeException {
    private ErrorCodes errorCode;
    private String message;

    public MemoryException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public MemoryException(ErrorCodes errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String msg() {
        return "";
    }

    public enum ErrorCodes {
        UnknownError,

        FailedToCreateCollection,

        FailedToDeleteCollection,

        UnableToDeserializeMetadata,

        AttemptedToAccessNonexistentCollection;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
