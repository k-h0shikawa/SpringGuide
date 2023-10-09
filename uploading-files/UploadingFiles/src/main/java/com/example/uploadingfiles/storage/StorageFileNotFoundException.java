package com.example.uploadingfiles.storage;

import org.springframework.boot.autoconfigure.web.WebProperties;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String message){super(message);}

    public StorageFileNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
