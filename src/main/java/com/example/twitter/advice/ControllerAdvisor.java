package com.example.twitter.advice;

import com.example.twitter.controller.dto.TwitterErrorDto;
import com.example.twitter.controller.dto.users.UserDto;
import com.example.twitter.exception.EmptyInputException;
import com.example.twitter.exception.NoResourceException;
import com.example.twitter.exception.ResourceAlreadyExistingException;
import com.example.twitter.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public TwitterErrorDto handleAlreadyExistingUser(UserAlreadyExistException userAlreadyExistException) {

        return new TwitterErrorDto(userAlreadyExistException.getErrorCode(), List.of(userAlreadyExistException.getMessage()));

    }

    @ExceptionHandler(value = NoResourceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TwitterErrorDto handleNoResource(NoResourceException noResourceException) {

        return new TwitterErrorDto(noResourceException.getErrorCode(), List.of(noResourceException.getMessage()));

    }


    @ExceptionHandler(EmptyInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TwitterErrorDto handleEmptyInput(EmptyInputException emptyInputException) {

        return new TwitterErrorDto(emptyInputException.getErrorCode(), List.of(emptyInputException.getMessage()));

    }

    @ExceptionHandler(ResourceAlreadyExistingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public TwitterErrorDto handleAlreadyExistingResource(ResourceAlreadyExistingException resourceAlreadyExistingException) {

        return new TwitterErrorDto(resourceAlreadyExistingException.getErrorCode(), List.of(resourceAlreadyExistingException.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TwitterErrorDto handleMethodNotValidArgument(MethodArgumentNotValidException ex) {
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new TwitterErrorDto("#methodArgumentNotValid", validationErrors);
    }

}
