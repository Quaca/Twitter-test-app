package com.example.twitter.exception;

public class EmailExistsException extends Throwable{

    public EmailExistsException(String message){
        super(message);
    }
}
