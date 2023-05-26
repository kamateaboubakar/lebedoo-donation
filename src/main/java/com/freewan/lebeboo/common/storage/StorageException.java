package com.freewan.lebeboo.common.storage;

public class StorageException extends RuntimeException {

    public StorageException() {
        super("File must not be empty.");
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
