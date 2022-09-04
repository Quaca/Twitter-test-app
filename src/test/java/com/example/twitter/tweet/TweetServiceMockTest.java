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

    private Tweet postResponseTweet;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        tweet = new Tweet();
//        tweet.setId(1L);
//        tweet.setText("Test");
//        tweet.setPublishedAt(new Date());
//        tweet.setUpdatedAt(new Date());
//        tweet.setUserId("userId1");
//
//        postResponseTweet = new Tweet();
//        postResponseTweet.setId(1L);
//        postResponseTweet.setText("Test");
//        postResponseTweet.setPublishedAt(new Date());
//        postResponseTweet.setUpdatedAt(new Date());
//        postResponseTweet.setUserId("userId2");
    }


    @Test
    public void getTweetById_Successful_ValidId() {
//        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
//        Tweet actualPostResponseTweet = tweetService.getTweetById(1L);
//
//        assertThat(actualPostResponseTweet.getId(), is(equalTo(postResponseTweet.getId())));
//        assertThat(actualPostResponseTweet.getText(), is(equalTo(postResponseTweet.getText())));
//        assertThat(actualPostResponseTweet.getUserId(), is(equalTo(postResponseTweet.getUserId())));

    }

    @Test
    public void save_Successful_TweetObjectPassed() {

//        Tweet newTweet = new Tweet();
//        newTweet.setId(10L);
//        newTweet.setText("Test for save");
//        newTweet.setUpdatedAt(new Date());
//        newTweet.setPublishedAt(new Date());
////        User author = new User();
////        author.setId("1L");
////        author.setName("John");
////        author.setSurname("Doe");
//        newTweet.setUserId("userId1");
//
//        when(tweetRepository.save(any(Tweet.class))).thenReturn(newTweet);
//
//        Tweet returnedTweet = tweetService.save(newTweet);
//
//        verify(tweetRepository, times(1)).save(newTweet);

    }


}
