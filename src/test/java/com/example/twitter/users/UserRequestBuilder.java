package com.example.twitter.users;

import com.example.twitter.service.TokenAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserRequestBuilder {

    private final MockMvc mvc;
    private final String token;

    public UserRequestBuilder(MockMvc mvc) {
        this.mvc = mvc;
        token = TokenAuthenticationService.createToken("akva");
    }

    ResultActions findUserById(Long id) throws Exception{
        return mvc.perform(get("/v1/user/{id}", id)
                .header("Authorization", token));
    }
    ResultActions createUser(String json) throws Exception {
        return mvc.perform(post("/v1/users")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }
    ResultActions updateUser(String json) throws Exception {
        return mvc.perform(put("/v1/users")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }
    ResultActions deleteUserById(Long id) throws Exception {
        return mvc.perform(delete("/v1/user")
                .header("Authorization", token));
    }
}
