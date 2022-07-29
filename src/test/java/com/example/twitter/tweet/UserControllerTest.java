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
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    private TweetRequestBuilder tweetRequestBuilder;

    @BeforeEach
    void configureSystemUnderTest() {
        tweetRequestBuilder = new TweetRequestBuilder(mvc);
    }

    @Test
    @Transactional
    public void getUserById_Successful_ValidId() throws Exception {
        tweetRequestBuilder.findUserById(78L)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getUserById_Unsuccessful_NotExistingId() throws Exception {
        tweetRequestBuilder.findUserById(1000000L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#UserNotExisting"));
    }

    @Test
    @Transactional
    public void createUser_Successful_UserObjectPassed() throws Exception {
        String json = "{\"name\":\"Steve\",\"surname\":\"Jobs\",\"country\":\"USA\"}";
        tweetRequestBuilder.createUser(json)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void updateUser_Successful_UserObjectPassed() throws Exception {
        String json = "{\"id\":78,\"name\":\"Steve\",\"surname\":\"Jobs\",\"country\":\"USA-America\"}";
        tweetRequestBuilder.updateUser(json)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteUserById_Successful_Id() throws Exception {
        tweetRequestBuilder.deleteUserById(78L)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteUserById_Unsuccessful_Id() throws Exception {
        tweetRequestBuilder.deleteUserById(100453L)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#UserNotExisting"));
    }


}
