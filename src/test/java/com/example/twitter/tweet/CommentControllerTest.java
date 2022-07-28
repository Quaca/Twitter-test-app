package com.example.twitter.tweet;

import com.example.twitter.service.TweetService;
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
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    private TweetRequestBuilder tweetRequestBuilder;

    @Autowired
    private TweetService tweetService;

    @BeforeEach
    void configureSystemUnderTest() {
        tweetRequestBuilder = new TweetRequestBuilder(mvc);
    }

    @Test
    @Transactional
    public void deleteCommentById_Successful_Id() throws Exception {
        tweetRequestBuilder.deleteCommentById(50L)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteCommentById_Unsuccessful_Id() throws Exception {
        tweetRequestBuilder.deleteCommentById(100453L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#CommentNotExisting"));
    }
}
