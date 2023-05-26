package com.freewan.lebeboo.common.storage;

public class FileTypeException extends StorageException {
    public FileTypeException() {
        super("This file is not an image.");
    }
}
