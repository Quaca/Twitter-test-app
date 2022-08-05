package com.example.twitter.users;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserRequestBuilder {

    private final MockMvc mvc;

    public UserRequestBuilder(MockMvc mvc) {
        this.mvc = mvc;
    }

    ResultActions findUserById(Long id) throws Exception{
        return mvc.perform(get("/v1/user/{id}", id));
    }
    ResultActions createUser(String json) throws Exception {
        return mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }
    ResultActions updateUser(String json) throws Exception {
        return mvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }
    ResultActions deleteUserById(Long id) throws Exception {
        return mvc.perform(delete("/v1/user/{id}", id));
    }
}
