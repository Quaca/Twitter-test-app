package com.example.twitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TwitterErrorDto {
    private String errorCode;
    private List<String> message;
}
