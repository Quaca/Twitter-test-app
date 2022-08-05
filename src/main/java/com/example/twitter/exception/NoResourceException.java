package com.example.twitter.exception;

public class NoResourceException extends RuntimeException {

    private String resourceId;
    private String errorCode;

    public NoResourceException(String resourceId, String message, String errorCode) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }

    public NoResourceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoResourceException(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
