package com.example.twitter.controller;

import com.example.twitter.controller.dto.users.UserCreateDto;
import com.example.twitter.controller.dto.users.UserGetDto;
import com.example.twitter.controller.dto.users.UserUpdateDto;
import com.example.twitter.model.User;
import com.example.twitter.service.UserService;
import com.example.twitter.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ObjectMapperUtils.map(user, UserGetDto.class);
    }

    @PostMapping("v1/users")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User newUser = ObjectMapperUtils.map(userCreateDto, User.class);

        userService.save(newUser);

        return ObjectMapperUtils.map(newUser, UserGetDto.class);
    }

    @PutMapping("v1/users")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        User newUser = ObjectMapperUtils.map(userUpdateDto, User.class);
        userService.update(newUser);
        return ObjectMapperUtils.map(newUser, UserGetDto.class);
    }

    @DeleteMapping("v1/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String id) {
        User user = userService.getUserById(Long.valueOf(id));
        userService.delete(user);
    }

}
