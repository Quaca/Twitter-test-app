package com.example.twitter.controller.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto extends UserDto {
    @NotNull
    private Long id;
}
