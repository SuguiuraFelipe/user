package com.suguiura.user.infrastructure.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String menssage, Throwable throwable) {
        super(menssage, throwable);
    }
}
