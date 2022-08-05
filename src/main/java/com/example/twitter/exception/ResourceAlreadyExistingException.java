package com.example.twitter.exception;

public class ResourceAlreadyExistingException extends RuntimeException{
    private String errorCode;

    public ResourceAlreadyExistingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
