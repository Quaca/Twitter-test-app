package com.example.twitter.tweet;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TweetRequestBuilder {

    private final MockMvc mvc;

    public TweetRequestBuilder(MockMvc mvc) {
        this.mvc = mvc;
    }

    ResultActions findAllTweets() throws Exception {
        return mvc.perform(get("/v1/tweets"));
    }

    ResultActions findTweetById(Long id) throws Exception {
        return mvc.perform(get("/v1/tweet/{id}", id));
    }

    ResultActions createTweet(String json) throws Exception {
        return mvc.perform(post("/v1/tweets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    ResultActions deleteTweetById(Long id) throws Exception {
        return mvc.perform(delete("/v1/tweet/{id}", id));
    }

}
