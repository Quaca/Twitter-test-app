package com.example.twitter.tweet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TweetControllerTest {

    @Autowired
    private MockMvc mvc;

    private TweetRequestBuilder tweetRequestBuilder;

    @BeforeEach
    void configureSystemUnderTest() {
        tweetRequestBuilder = new TweetRequestBuilder(mvc);
    }

    @Test
    @Transactional
    public void getTweets_Successful_IfEverythingIsGood() throws Exception {
        tweetRequestBuilder.findAllTweets()
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getTweetById_Successful_ValidId() throws Exception {
        tweetRequestBuilder.findTweetById(3L)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getTweetById_Unsuccessful_NotExistingId() throws Exception {
        tweetRequestBuilder.findTweetById(100L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#TweetNotExisting"));
    }

    @Test
    @Transactional
    public void postTweet_Successful_TweetObjectPassed() throws Exception {
        String json = "{\"text\":\"Utorak text objava\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.createTweet(json)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void postTweet_Unsuccessful_TweetObjectPassedWithoutText() throws Exception {
        String json = "{\"text\":\"\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.createTweet(json)
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void updateTweet_Successful_TweetObjectPassed() throws Exception {
        String json = "{\"id\":3,\"text\":\"Test update\",\"publishedAt\":\"2022-07-19T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-19T12:25:43.511+00:00\"}";
        tweetRequestBuilder.updateTweet(json)
                .andExpect(status().isOk());
    }


    @Test
    @Transactional
    public void deleteTweetById_Successful_Id() throws Exception {
        tweetRequestBuilder.deleteTweetById(3L)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteTweetById_Unsuccessful_Id() throws Exception {
        tweetRequestBuilder.deleteTweetById(100L)
                .andExpect(status().isNotFound());
    }


    // COMMENTS
    @Test
    @Transactional
    public void addComment_Successful_CommentObjectPassed() throws Exception {
        String json = "{\"text\":\"treci komentar\",\"tweetId\":49,\"publishedAt\":\"2022-07-28T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-28T12:25:43.511+00:00\"}";
        tweetRequestBuilder.addComment(json)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void addComment_Unsuccessful_CommentObjectPassedWithoutText() throws Exception {
        String json = "{\"text\":\"\",\"tweetId\":49,\"publishedAt\":\"2022-07-28T12:25:43.511+00:00\",\"updatedAt\":\"2022-07-28T12:25:43.511+00:00\"}";
        tweetRequestBuilder.addComment(json)
                .andExpect(status().isBadRequest());
    }


}
