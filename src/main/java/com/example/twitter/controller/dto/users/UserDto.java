package com.example.twitter.controller.dto.users;

import com.example.twitter.validation.PasswordMatches;
import com.example.twitter.validation.ValidEmail;
import com.example.twitter.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDto {
    @NotBlank
    @Size(min = 1, message = "{Size.userDto.name}")
    private String name;

    @NotBlank
    @Size(min = 1, message = "{Size.userDto.surname}")
    private String surname;

    @NotBlank
    @Size(min = 1, message = "{Size.userDto.country}")
    private String country;

    @NotBlank
    @Size(min = 1, message = "{Size.userDto.username}")
    private String username;

    @ValidEmail
    @NotBlank
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [name=")
                .append(name)
                .append(", surname=")
                .append(surname)
                .append(", email=")
                .append(email)
                .append(", country=")
                .append(country).append("]");
        return builder.toString();
    }
}
