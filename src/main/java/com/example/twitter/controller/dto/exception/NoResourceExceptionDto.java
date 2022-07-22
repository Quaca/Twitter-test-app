package com.example.twitter.controller.dto.exception;

import lombok.Data;

@Data
public class NoResourceExceptionDto extends ExceptionDto {
    private String resourceId;
}
