package com.example.twitter.controller;

import com.example.twitter.controller.dto.CommentDto;
import com.example.twitter.controller.dto.TweetCreateDto;
import com.example.twitter.controller.dto.TweetGetDto;
import com.example.twitter.controller.dto.TweetUpdateDto;
import com.example.twitter.model.Comment;
import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import com.example.twitter.service.CommentService;
import com.example.twitter.service.TweetService;
import com.example.twitter.service.UserService;
import com.example.twitter.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;
    private final CommentService commentService;

    public TweetController(TweetService tweetService, UserService userService, CommentService commentService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.commentService = commentService;
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
    public TweetGetDto addTweet(@Valid @RequestBody TweetCreateDto tweetCreateDto) {
        Tweet tweet = ObjectMapperUtils.map(tweetCreateDto, Tweet.class);
        User user = userService.getUserById(tweetCreateDto.getUserId());
        tweet.setUser(user);

        Tweet returnedTweet = tweetService.save(tweet);

        return ObjectMapperUtils.map(returnedTweet, TweetGetDto.class);
    }

    @PutMapping("v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto updateTweet(@Valid @RequestBody TweetUpdateDto tweetUpdateDto) {
        Tweet newTweet = ObjectMapperUtils.map(tweetUpdateDto, Tweet.class);
        Tweet returnedTweet = tweetService.update(newTweet);
        return ObjectMapperUtils.map(returnedTweet, TweetGetDto.class);
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

        Tweet tweet = tweetService.getTweetById(commentDto.getTweetId());
        User user = userService.getUserById(commentDto.getUserId());
        comment.setUser(user);

        tweetService.addComment(tweet, comment);

        Tweet newTweet = tweetService.getTweetById(commentDto.getTweetId());
        return ObjectMapperUtils.map(newTweet, TweetGetDto.class);
    }

    @DeleteMapping("v1/tweet/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable String id) {

        Comment comment = commentService.getCommentById(Long.valueOf(id));
        commentService.delete(comment);

    }
}
