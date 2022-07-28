package com.example.twitter.controller;

import com.example.twitter.controller.dto.*;
import com.example.twitter.model.Comment;
import com.example.twitter.model.Tweet;
import com.example.twitter.service.TweetService;
import com.example.twitter.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }


    @GetMapping("/v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetGetDto> getTweets() {
        List<Tweet> tweets = tweetService.getTweets();
        List<TweetGetDto> tweetsGetDto = ObjectMapperUtils.mapAll(tweets, TweetGetDto.class);
        return tweetsGetDto;
    }

    @GetMapping("/v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto getTweet(@PathVariable("id") Long id) {
        Tweet tweet = tweetService.getTweetById(id);
        return ObjectMapperUtils.map(tweet, TweetGetDto.class);
    }


    @PostMapping("v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetDto addTweet(@Valid @RequestBody TweetCreateDto tweetCreateDto) {
        Tweet newTweet = ObjectMapperUtils.map(tweetCreateDto, Tweet.class);
        tweetService.save(newTweet);

        return ObjectMapperUtils.map(newTweet, TweetCreateDto.class);
    }

    @PutMapping("v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetDto updateTweet(@Valid @RequestBody TweetUpdateDto tweetUpdateDto) {
        Tweet newTweet = ObjectMapperUtils.map(tweetUpdateDto, Tweet.class);
        tweetService.update(newTweet);
        return ObjectMapperUtils.map(newTweet, TweetUpdateDto.class);
    }

    @DeleteMapping("v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTweet(@PathVariable String id) {
        Tweet tweet = tweetService.getTweetById(Long.valueOf(id));
        tweetService.delete(tweet);
    }

    @PostMapping("v1/tweet/comments")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto addComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = ObjectMapperUtils.map(commentDto, Comment.class);
        Tweet tweet = tweetService.getTweetById(Long.valueOf(commentDto.getTweetId()));
        tweetService.addComment(tweet, comment);
        Tweet newTweet = tweetService.getTweetById(Long.valueOf(commentDto.getTweetId()));
        return ObjectMapperUtils.map(newTweet, TweetGetDto.class);
    }
}
