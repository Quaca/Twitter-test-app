package com.example.twitter.tweet;

import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import com.example.twitter.repository.TweetRepository;
import com.example.twitter.service.TweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class TweetServiceMockTest {

    @Mock
    private TweetRepository tweetRepository;
    @InjectMocks
    private TweetService tweetService;

    private Tweet tweet;
    private User user;

    private Tweet postResponseTweet;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(2L);
        user.setName("John");
        user.setSurname("Doe");
        tweet = new Tweet();
        tweet.setId(1L);
        tweet.setText("Test");
        tweet.setPublishedAt(new Date());
        tweet.setUpdatedAt(new Date());
        tweet.setUser(user);

        user2 = new User();
        user2.setId(2L);
        user2.setName("John");
        user2.setSurname("Doe");
        postResponseTweet = new Tweet();
        postResponseTweet.setId(1L);
        postResponseTweet.setText("Test");
        postResponseTweet.setPublishedAt(new Date());
        postResponseTweet.setUpdatedAt(new Date());
        postResponseTweet.setUser(user2);
    }


    @Test
    public void getTweetById_Successful_ValidId() {
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
        Tweet actualPostResponseTweet = tweetService.getTweetById(1L);

        assertThat(actualPostResponseTweet.getId(), is(equalTo(postResponseTweet.getId())));
        assertThat(actualPostResponseTweet.getText(), is(equalTo(postResponseTweet.getText())));
        assertThat(actualPostResponseTweet.getUser().getId(), is(equalTo(postResponseTweet.getUser().getId())));

    }

    @Test
    public void save_Successful_TweetObjectPassed() {

        Tweet newTweet = new Tweet();
        newTweet.setId(10L);
        newTweet.setText("Test for save");
        newTweet.setUpdatedAt(new Date());
        newTweet.setPublishedAt(new Date());
        User author = new User();
        author.setId(2L);
        author.setName("John");
        author.setSurname("Doe");
        newTweet.setUser(author);

        when(tweetRepository.save(any(Tweet.class))).thenReturn(newTweet);

        Tweet returnedTweet = tweetService.save(newTweet);

        verify(tweetRepository, times(1)).save(newTweet);

    }


}
