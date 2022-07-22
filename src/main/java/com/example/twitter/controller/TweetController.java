package com.example.twitter.controller;

import com.example.twitter.controller.dto.TweetDto;
import com.example.twitter.exception.EmptyInputException;
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
    public List<TweetDto> getTweets() {
        List<Tweet> tweets = tweetService.getTweets();
        List<TweetDto> tweetsDto = ObjectMapperUtils.mapAll(tweets, TweetDto.class);
        return tweetsDto;
    }

    @GetMapping("/v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetDto getTweet(@PathVariable("id") Long id) {
        Tweet tweet = tweetService.getTweetById(id);
        return ObjectMapperUtils.map(tweet, TweetDto.class);
    }


    @PostMapping("v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetDto addTweet(@Valid @RequestBody TweetDto tweetDto) {
        Tweet newTweet = ObjectMapperUtils.map(tweetDto, Tweet.class);
        tweetService.save(newTweet);
        return ObjectMapperUtils.map(newTweet, TweetDto.class);
    }

    @PutMapping("v1/tweet")
    @ResponseStatus(HttpStatus.OK)
    public TweetDto updateTweet(@Valid @RequestBody TweetDto tweetDto) {
        Tweet newTweet = ObjectMapperUtils.map(tweetDto, Tweet.class);
        if (newTweet.getId() == null) {
            throw new EmptyInputException("tweet.id", "Id of updating tweet is missing", "#tweetIdMissing");
        }
        tweetService.update(newTweet);

        return ObjectMapperUtils.map(newTweet, TweetDto.class);
    }

    @DeleteMapping("v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTweet(@PathVariable String id) {

        Tweet tweet = tweetService.getTweetById(Long.valueOf(id));
        tweetService.delete(tweet);
        return;

    }


}
