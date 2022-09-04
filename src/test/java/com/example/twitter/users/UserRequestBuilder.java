package com.example.twitter.users;

import com.example.twitter.model.User;
import com.example.twitter.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserRequestBuilder {

    private final KeycloakService keycloakService;
    private final MockMvc mvc;
    private final String token;

    public UserRequestBuilder(KeycloakService keycloakService, MockMvc mvc) {
        this.keycloakService = keycloakService;
        this.mvc = mvc;
        User user = new User();
        user.setUsername("third_user");
        user.setPassword("rootpass16");
        token = "Bearer " + this.keycloakService.loginUserStringToken(user);
    }

    ResultActions findUserById(String id) throws Exception{
        return mvc.perform(get("/v1/user/{id}", id)
                .header("Authorization",token));
    }
    ResultActions createUser(String json) throws Exception {
        return mvc.perform(post("/v1/auth/register")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

}
