package com.example.twitter.tweet;

import com.example.twitter.IntegrationTest;
import com.example.twitter.service.TokenAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TweetControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mvc;
//    @Autowired
//    private TokenAuthenticationService tokenAuthenticationService;

    private TweetRequestBuilder tweetRequestBuilder;

    @BeforeEach
    void configureSystemUnderTest() {
        tweetRequestBuilder = new TweetRequestBuilder(mvc);
    }

    @Test
    public void getTweets_Successful_IfEverythingIsGood() throws Exception {
        tweetRequestBuilder.findAllTweets()
                .andExpect(status().isOk());
    }

    @Test
    public void getTweetById_Successful_ValidId() throws Exception {
        tweetRequestBuilder.findTweetById(79L)
                .andExpect(status().isOk());
    }

    @Test
    public void getTweetById_Unsuccessful_NotExistingId() throws Exception {
        tweetRequestBuilder.findTweetById(1000000L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#TweetNotExisting"));
    }

    @Test
    public void postTweet_Successful_TweetObjectPassed() throws Exception {
        String json = "{\"text\":\"Test\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.createTweet(json)
                .andExpect(status().isOk());
    }

    @Test
    public void postTweet_Unsuccessful_TweetObjectPassedWithoutText() throws Exception {
        String json = "{\"text\":\"\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.createTweet(json)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTweet_Successful_TweetObjectPassed() throws Exception {
        String json = "{\"text\":\"Test update\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.updateTweet(json)
                .andExpect(status().isOk());
    }


    @Test
    public void deleteTweetById_Successful_Id() throws Exception {
        tweetRequestBuilder.deleteTweetById(79L)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTweetById_Unsuccessful_Id() throws Exception {
        tweetRequestBuilder.deleteTweetById(1000000L)
                .andExpect(status().isNotFound());
    }


    // COMMENTS
    @Test
    public void addComment_Successful_CommentObjectPassed() throws Exception {
        String json = "{\"text\":\"comment\",\"tweetId\":79,\"publishedAt\":\"2022-07-28T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-28T12:25:43.511+00:00\"}";
        tweetRequestBuilder.addComment(json)
                .andExpect(status().isOk());
    }

    @Test
    public void addComment_Unsuccessful_CommentObjectPassedWithoutText() throws Exception {
        String json = "{\"text\":\"\",\"tweetId\":49,\"publishedAt\":\"2022-07-28T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-28T12:25:43.511+00:00\"}";
        tweetRequestBuilder.addComment(json)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCommentById_Successful_Id() throws Exception {
        tweetRequestBuilder.deleteCommentById(85L)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentById_Unsuccessful_Id() throws Exception {
        tweetRequestBuilder.deleteCommentById(100453L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#CommentNotExisting"));
    }


}
