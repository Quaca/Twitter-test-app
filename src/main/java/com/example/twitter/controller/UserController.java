package com.example.twitter.controller;

import com.example.twitter.controller.dto.TweetGetDto;
import com.example.twitter.controller.dto.users.UserDto;
import com.example.twitter.controller.dto.users.UserGetDto;
import com.example.twitter.controller.dto.users.UserLoginDto;
import com.example.twitter.exception.ResourceAlreadyExistingException;
import com.example.twitter.model.User;
import com.example.twitter.service.KeycloakService;
import com.example.twitter.service.TweetService;
import com.example.twitter.service.UserService;
import com.example.twitter.utils.ObjectMapperUtils;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    private final TweetService tweetService;
    private final KeycloakService keycloakService;
    private ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, TweetService tweetService, KeycloakService keycloakService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.keycloakService = keycloakService;
        this.eventPublisher = eventPublisher;
    }

//    @GetMapping("/v1/users")
//    @ResponseStatus(HttpStatus.OK)
//    public String getUsers() {
//        KeycloakAuthenticationToken authentication =
//                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//
//        Principal principal = (Principal) authentication.getPrincipal();
//
//        String userIdByToken = "";
//
//        if (principal instanceof KeycloakPrincipal) {
//
//            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
//            IDToken token = kPrincipal.getKeycloakSecurityContext()
//                    .getToken();
//
//            userIdByToken = token.getSubject();
//
//        }
//
//        return null;
////        List users = keycloakService.getKeycloakUsers();
////        return users;
//    }


    @PostMapping("/v1/auth/register")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto registerUser(@Valid @RequestBody UserDto userDto){
        User newUser = ObjectMapperUtils.map(userDto, User.class);

        User returnedUser = keycloakService.registerNewUser(newUser);

        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
    }

    @PostMapping("/v1/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse loginUser(@Valid @RequestBody UserLoginDto userLoginDto){
        User user = ObjectMapperUtils.map(userLoginDto, User.class);

        AccessTokenResponse returnedUser = keycloakService.loginUser(user);

        return returnedUser;
    }


    @GetMapping("/v1/user")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto getCurrentUser() {

        User user = keycloakService.extractUserFromToken();
        UserGetDto userGetDto = ObjectMapperUtils.map(user, UserGetDto.class);
        userGetDto.setTweets(ObjectMapperUtils.mapAll(tweetService.getUserTweets(user.getId()), TweetGetDto.class));

        return userGetDto;

    }


    @GetMapping("/v1/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDto getUser(@PathVariable String userId) {

        UserRepresentation userRepresentation = keycloakService.findUserById(userId);

        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setName(userRepresentation.getFirstName());
        userGetDto.setSurname(userRepresentation.getLastName());
        userGetDto.setEmail(userRepresentation.getEmail());
        userGetDto.setUsername(userRepresentation.getUsername());
        userGetDto.setTweets(ObjectMapperUtils.mapAll(tweetService.getUserTweets(userId), TweetGetDto.class));

        return userGetDto;
    }

    @PostMapping("v1/users/follow/{userId}")
    public UserGetDto followUser(@PathVariable String userId) {

        User user = keycloakService.extractUserFromToken();

        User userAfterFollow = userService.followUser(user, userId);

        return ObjectMapperUtils.map(userAfterFollow, UserGetDto.class);
    }

    @DeleteMapping("v1/users/unfollow/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void unfollowUser(@PathVariable String userId) {

        User user = keycloakService.extractUserFromToken();

        userService.unfollowUser(user, userId);

    }


//    @PostMapping("v1/users")
//    @ResponseStatus(HttpStatus.OK)
//    public UserGetDto createUser(@Valid @RequestBody UserDto userDto, final HttpServletRequest request) {
//        User newUser = ObjectMapperUtils.map(userDto, User.class);
//        User returnedUser = userService.save(newUser);
//
//        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(returnedUser, getAppUrl(request), request.getLocale()));
//        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
//    }
//
//    @PutMapping("v1/users")
//    @ResponseStatus(HttpStatus.OK)
//    public UserGetDto updateUser(@Valid @RequestBody UserDto userDto) {
//        User user = ObjectMapperUtils.map(userDto, User.class);
//        User userFromToken = UserContextHolder.get().getUser();
//        user.setId(userFromToken.getId());
//
//        User returnedUser = userService.update(user);
//        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
//    }
//
//    @DeleteMapping("v1/user")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize(value = "ADMIN_ROLE")
//    public void deleteUser() {
//        User user = UserContextHolder.get().getUser();
//        userService.delete(user);
//    }
//
//    @PostMapping("v1/users/follow/{id}")
//    public UserGetDto followUser(@PathVariable String id) {
//
//        User user = UserContextHolder.get().getUser();
//
//        User userAfterFollow = userService.followUser(user, id);
//
//        return ObjectMapperUtils.map(userAfterFollow, UserGetDto.class);
//    }
//
//    @DeleteMapping("v1/users/unfollow/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserGetDto unfollowUser(@PathVariable String id) {
//
//        User user = UserContextHolder.get().getUser();
//
//        User userAfterUnfollow = userService.unfollowUser(user, id);
//
//        return ObjectMapperUtils.map(userAfterUnfollow, UserGetDto.class);
//    }
//
//    @PostMapping("v1/users/role")
//    @ResponseStatus(HttpStatus.OK)
//    public UserGetDto addRole(@Valid @RequestBody RoleCreateDto roleCreateDto) {
//
//        User returnedUser = userService.addRoleToUser(roleCreateDto.getUsername(), roleCreateDto.getRoleName());
//
//        return ObjectMapperUtils.map(returnedUser, UserGetDto.class);
//
//    }
//
//    @GetMapping("/registrationConfirm")
//    @ResponseStatus(HttpStatus.OK)
//    public void confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token) {
//        Locale locale = request.getLocale();
//        String result = userService.validateVerificationToken(token);
//        if (result.equals("valid")) {
//            final User user = userService.getUser(token);
//        }
//    }
//
//    @PostMapping("/resetPassword")
//    @ResponseStatus(HttpStatus.OK)
//    public void resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
//        User user = userService.getUserByEmail(userEmail);
//        userService.sendMail(getAppUrl(request), request.getLocale(), user);
//    }
//
//    @PostMapping("/user/savePassword")
//    @ResponseStatus(HttpStatus.OK)
//    public void savePassword(HttpServletRequest request, @Valid PasswordDto passwordDto) {
//        final String result = userService.validatePasswordResetToken(passwordDto.getToken());
//        User user = userService.getUserByPasswordResetToken(passwordDto.getToken());
//        User returnedUser = userService.changeUserPassword(user, passwordDto.getNewPassword());
//
//    }
//
//
//    private String getAppUrl(HttpServletRequest request) {
//        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//    }

}
