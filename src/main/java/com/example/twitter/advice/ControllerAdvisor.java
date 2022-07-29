package com.example.twitter.advice;

import com.example.twitter.controller.dto.TwitterErrorDto;
import com.example.twitter.exception.EmptyInputException;
import com.example.twitter.exception.NoResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = NoResourceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TwitterErrorDto handleNoResource(NoResourceException noResourceException) {

        return new TwitterErrorDto(noResourceException.getErrorCode(), List.of(noResourceException.getMessage()));

//        NoResourceExceptionDto noResourceExceptionDto = new NoResourceExceptionDto();
//        noResourceExceptionDto.setResourceId(noResourceException.getResourceId());
//        noResourceExceptionDto.setMessage(noResourceException.getMessage());
//        noResourceExceptionDto.setErrorCode(noResourceException.getErrorCode());
//        return noResourceExceptionDto;
    }


    @ExceptionHandler(EmptyInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TwitterErrorDto handleEmptyInput(EmptyInputException emptyInputException) {

        return new TwitterErrorDto(emptyInputException.getErrorCode(), List.of(emptyInputException.getMessage()));
//        EmptyInputExceptionDto emptyInputExceptionDto = new EmptyInputExceptionDto();
//        emptyInputExceptionDto.setInputField(emptyInputException.getInputField());
//        emptyInputExceptionDto.setMessage(emptyInputException.getMessage());
//        emptyInputExceptionDto.setErrorCode(emptyInputException.getErrorCode());
//        return emptyInputExceptionDto;
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
