package com.example.twitter.validation;

import com.example.twitter.controller.dto.users.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final UserDto userDto = (UserDto) value;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
