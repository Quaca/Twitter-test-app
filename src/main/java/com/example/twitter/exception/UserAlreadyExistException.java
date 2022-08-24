package com.example.twitter.exception;

public class UserAlreadyExistException extends RuntimeException{

    private String errorCode;


    public UserAlreadyExistException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserAlreadyExistException() {
        super();
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
