package com.example.twitter.controller;

import com.example.twitter.context.UserContextHolder;
import com.example.twitter.controller.dto.PasswordDto;
import com.example.twitter.controller.dto.followership.FollowershipDto;
import com.example.twitter.controller.dto.users.RoleCreateDto;
import com.example.twitter.controller.dto.users.UserDto;
import com.example.twitter.controller.dto.users.UserGetDto;
import com.example.twitter.listener.OnRegistrationCompleteEvent;
import com.example.twitter.model.User;
import com.example.twitter.service.UserService;
import com.example.twitter.utils.ObjectMapperUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
public class UserController {
    private final UserService userService;
    private ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/v1/user")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto getCurrentUser() {
        User user = UserContextHolder.get().getUser();
        return ObjectMapperUtils.map(user, UserGetDto.class);
    }

    @GetMapping("/v1/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto getUser(@PathVariable String id) {
        User user = userService.getUserById(Long.valueOf(id));
        return ObjectMapperUtils.map(user, UserGetDto.class);
    }

    @PostMapping("v1/users")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto createUser(@Valid @RequestBody UserDto userDto, final HttpServletRequest request) {
        User newUser = ObjectMapperUtils.map(userDto, User.class);
        User returnedUser = userService.save(newUser);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(returnedUser, getAppUrl(request), request.getLocale()));
        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
    }

    @PutMapping("v1/users")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto updateUser(@Valid @RequestBody UserDto userDto) {
        User user = ObjectMapperUtils.map(userDto, User.class);
        User userFromToken = UserContextHolder.get().getUser();
        user.setId(userFromToken.getId());

        User returnedUser = userService.update(user);
        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
    }

    @DeleteMapping("v1/user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "ADMIN_ROLE")
    public void deleteUser() {
        User user = UserContextHolder.get().getUser();
        userService.delete(user);
    }

    @PostMapping("v1/users/follow/{id}")
    public UserGetDto followUser(@PathVariable String id) {

        User user = UserContextHolder.get().getUser();

        User userAfterFollow = userService.followUser(user, id);

        return ObjectMapperUtils.map(userAfterFollow, UserGetDto.class);
    }

    @DeleteMapping("v1/users/unfollow/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto unfollowUser(@PathVariable String id) {

        User user = UserContextHolder.get().getUser();

        User userAfterUnfollow = userService.unfollowUser(user, id);

        return ObjectMapperUtils.map(userAfterUnfollow, UserGetDto.class);
    }

    @PostMapping("v1/users/role")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto addRole(@Valid @RequestBody RoleCreateDto roleCreateDto) {

        User returnedUser = userService.addRoleToUser(roleCreateDto.getUsername(), roleCreateDto.getRoleName());

        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);

    }

    @GetMapping("/registrationConfirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token) {
        Locale locale = request.getLocale();
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
        }
    }

    @PostMapping("/resetPassword")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        userService.sendMail(getAppUrl(request), request.getLocale(), user);
    }

    @PostMapping("/user/savePassword")
    @ResponseStatus(HttpStatus.OK)
    public void savePassword(HttpServletRequest request, @Valid PasswordDto passwordDto) {
        final String result = userService.validatePasswordResetToken(passwordDto.getToken());
        User user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        User returnedUser = userService.changeUserPassword(user, passwordDto.getNewPassword());

    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
