package com.example.twitter.controller.dto.exception;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmptyInputExceptionDto extends ExceptionDto {
    private String inputField;
}
