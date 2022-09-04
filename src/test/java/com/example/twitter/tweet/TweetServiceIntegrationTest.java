package com.example.twitter.tweet;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import com.example.twitter.service.TweetService;
import com.example.twitter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
public class TweetServiceIntegrationTest {
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void getTweetById_Successful_ValidId() {

        Tweet actualPostResponseTweet = tweetService.getTweetById(79L);

        assertThat(actualPostResponseTweet.getId(), is(equalTo(79L)));
        assertThat(actualPostResponseTweet.getText(), is(equalTo("tweet sa userom")));

    }

    @Test
    @Transactional
    public void save_Successful_TweetObject() {

//        User user = userService.getUserById(78L);
//
//        Tweet newTweet = new Tweet();
//        newTweet.setText("Test");
//        newTweet.setUpdatedAt(new Date());
//        newTweet.setPublishedAt(new Date());
//        newTweet.setUser(user);
//
//        Tweet returnedTweet = tweetService.save(newTweet);
//
//        assertEquals(returnedTweet.getId(), newTweet.getId());

    }

    @Test
    public void update_Unsuccessful_TweetObjectPassedWithoutText() {

//        Tweet newTweet = new Tweet();
//        newTweet.setId(10L);
//        newTweet.setText("");
//        newTweet.setUpdatedAt(new Date());
//        newTweet.setPublishedAt(new Date());
//        User author = new User();
//        author.setId(2L);
//        author.setName("John");
//        author.setSurname("Doe");
//        newTweet.setUser(author);
//
//        assertThatThrownBy(() -> {
//            tweetService.update(newTweet);
//        }).isInstanceOf(NoResourceException.class).message();

    }
}
