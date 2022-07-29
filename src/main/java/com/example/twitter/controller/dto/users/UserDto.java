package com.example.twitter.controller.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String country;
}
