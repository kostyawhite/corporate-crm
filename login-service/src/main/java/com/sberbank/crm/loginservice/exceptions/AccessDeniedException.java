package com.sberbank.crm.loginservice.exceptions;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException() {

    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
