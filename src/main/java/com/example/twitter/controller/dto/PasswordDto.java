package com.example.twitter.controller.dto;

import com.example.twitter.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDto {
    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;
}
