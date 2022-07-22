package com.example.twitter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmptyInputException extends RuntimeException {
    private String inputField;
    private String errorCode;

    public EmptyInputException(String inputField, String message, String errorCode) {
        super(message);
        this.inputField = inputField;
        this.errorCode = errorCode;
    }
}
