package com.freewan.lebeboo.common.storage;

import com.freewan.lebeboo.exception.DataNotFoundException;

public class StorageFileNotFoundException extends DataNotFoundException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
