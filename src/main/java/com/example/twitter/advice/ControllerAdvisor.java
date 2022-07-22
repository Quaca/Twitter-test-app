package com.example.twitter.advice;

import com.example.twitter.controller.dto.exception.EmptyInputExceptionDto;
import com.example.twitter.controller.dto.exception.NoResourceExceptionDto;
import com.example.twitter.exception.EmptyInputException;
import com.example.twitter.exception.NoResourceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoResourceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public NoResourceExceptionDto handleNoResource(NoResourceException noResourceException) {

        NoResourceExceptionDto noResourceExceptionDto = new NoResourceExceptionDto();
        noResourceExceptionDto.setResourceId(noResourceException.getResourceId());
        noResourceExceptionDto.setMessage(noResourceException.getMessage());
        noResourceExceptionDto.setErrorCode(noResourceException.getErrorCode());

        return noResourceExceptionDto;
    }


    @ExceptionHandler(EmptyInputException.class)
    public EmptyInputExceptionDto handleEmptyInput(EmptyInputException emptyInputException) {
        EmptyInputExceptionDto emptyInputExceptionDto = new EmptyInputExceptionDto();
        emptyInputExceptionDto.setInputField(emptyInputException.getInputField());
        emptyInputExceptionDto.setMessage(emptyInputException.getMessage());
        emptyInputExceptionDto.setErrorCode(emptyInputException.getErrorCode());

        return emptyInputExceptionDto;
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

    }
}
