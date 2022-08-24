package com.example.twitter.tweet;

import com.example.twitter.service.TokenAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TweetRequestBuilder {

    private final MockMvc mvc;
    private String token;

//    private final TokenAuthenticationService tokenAuthenticationService;

    public TweetRequestBuilder(MockMvc mvc) {
        this.mvc = mvc;
//        this.tokenAuthenticationService = tokenAuthenticationService;
        token = TokenAuthenticationService.createToken("akva");
    }

    ResultActions findAllTweets() throws Exception {
        return mvc.perform(get("/v1/tweets").header("Authorization", token));
    }

    ResultActions findTweetById(Long id) throws Exception {
        return mvc.perform(get("/v1/tweet/{id}", id).header("Authorization", token));
    }

    ResultActions createTweet(String json) throws Exception {
        return mvc.perform(post("/v1/tweets")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    ResultActions updateTweet(String json) throws Exception {
        return mvc.perform(put("/v1/tweets")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    ResultActions deleteTweetById(Long id) throws Exception {
        return mvc.perform(delete("/v1/tweet/{id}", id)
                .header("Authorization", token));
    }

    ResultActions addComment(String json) throws Exception {
        return mvc.perform(post("/v1/tweet/comments")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    ResultActions deleteCommentById(Long id) throws Exception {
        return mvc.perform(delete("/v1/tweet/comment/{id}", id)
                .header("Authorization", token));
    }

}
