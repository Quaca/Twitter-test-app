package com.example.twitter.exception;

public class NotValidOwnerException extends RuntimeException {
    private String userId;
    private String errorCode;

    public NotValidOwnerException(String userId, String message, String errorCode) {
        super(message);
        this.userId = userId;
        this.errorCode = errorCode;
    }

    public NotValidOwnerException(String userId, String errorCode) {
        super("Not valid owner");
        this.userId = userId;
        this.errorCode = errorCode;
    }

    public NotValidOwnerException(String userId) {
        super("Not valid owner");
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
