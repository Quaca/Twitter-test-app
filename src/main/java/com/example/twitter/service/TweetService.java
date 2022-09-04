package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.model.Comment;
import com.example.twitter.model.Tweet;
import com.example.twitter.repository.TweetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Transactional(readOnly = true)
    public List<Tweet> getTweets() {
        return tweetRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tweet> getUserTweets(String userId) {
        List<Tweet> tweets = tweetRepository.findTweetsByUserIdEquals(userId).orElseThrow(() -> new NoResourceException(userId, "There is no tweets for given user", "#TweetNotExisting"));
        return tweets;
    }

    @Transactional(readOnly = true)
    public Tweet getTweetById(Long id) throws NoResourceException {
        return tweetRepository.findById(id).orElseThrow(() -> new NoResourceException(String.valueOf(id), "There is no tweet with given id", "#TweetNotExisting"));
    }

    @Transactional
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Transactional
    public Tweet update(Tweet tweet) {

        // get existing tweet for database
        Tweet oldTweet = getTweetById(Long.valueOf(tweet.getId()));

        // update values
        oldTweet.setText(tweet.getText());
        oldTweet.setPublishedAt(tweet.getPublishedAt());
        oldTweet.setUpdatedAt(tweet.getUpdatedAt());

        // save to database
        return tweetRepository.save(oldTweet);
    }

    @Transactional
    public void deleteById(Long id) {
        tweetRepository.deleteById(id);
    }

    @Transactional
    public void delete(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

    @Transactional
    public void addComment(Tweet tweet, Comment comment) {
        tweet.addComment(comment);
        tweetRepository.save(tweet);
    }
}
