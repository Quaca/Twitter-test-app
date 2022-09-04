package com.example.twitter.controller;

import com.example.twitter.controller.dto.CommentDto;
import com.example.twitter.controller.dto.TweetCreateDto;
import com.example.twitter.controller.dto.TweetGetDto;
import com.example.twitter.controller.dto.TweetUpdateDto;
import com.example.twitter.exception.NotValidOwnerException;
import com.example.twitter.model.Comment;
import com.example.twitter.model.Tweet;
import com.example.twitter.service.CommentService;
import com.example.twitter.service.KeycloakService;
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
    private final KeycloakService keycloakService;
    private final CommentService commentService;

    public TweetController(TweetService tweetService, UserService userService, KeycloakService keycloakService, CommentService commentService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.commentService = commentService;
    }


    @GetMapping("/v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetGetDto> getCurrentUserTweets() {

        String userIdByToken = keycloakService.extractUserId();
        List<Tweet> tweets = tweetService.getUserTweets(userIdByToken);
        List<TweetGetDto> tweetsGetDto = ObjectMapperUtils.mapAll(tweets, TweetGetDto.class);
        return tweetsGetDto;

    }

    @GetMapping("/v1/tweets/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetGetDto> getUserTweets(@PathVariable("id") String userId) {
        List<Tweet> tweets = tweetService.getUserTweets(userId);
        List<TweetGetDto> tweetsGetDto = ObjectMapperUtils.mapAll(tweets, TweetGetDto.class);
        return tweetsGetDto;
    }

    @GetMapping("/v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto getTweet(@PathVariable("id") Long id) {
        Tweet tweet = tweetService.getTweetById(id);
        return ObjectMapperUtils.map(tweet, TweetGetDto.class);
    }


    @PostMapping("/v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto addTweet(@Valid @RequestBody TweetCreateDto tweetCreateDto) {
        Tweet tweet = ObjectMapperUtils.map(tweetCreateDto, Tweet.class);
        String userIdByToken = keycloakService.extractUserId();

        tweet.setUserId(userIdByToken);
        Tweet returnedTweet = tweetService.save(tweet);

        return ObjectMapperUtils.map(returnedTweet, TweetGetDto.class);
    }

    @PutMapping("/v1/tweets")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto updateTweet(@Valid @RequestBody TweetUpdateDto tweetUpdateDto) {
        Tweet tweet = ObjectMapperUtils.map(tweetUpdateDto, Tweet.class);
        Tweet existingTweet = tweetService.getTweetById(tweet.getId());

        String userIdByToken = keycloakService.extractUserId();
        if (!userIdByToken.equals(existingTweet.getUserId())) {
            throw new NotValidOwnerException(existingTweet.getUserId(), "#NotValidOwner");
        }

        existingTweet.setText(tweet.getText());
        Tweet returnedTweet = tweetService.update(existingTweet);
        return ObjectMapperUtils.map(returnedTweet, TweetGetDto.class);

    }

    @DeleteMapping("v1/tweet/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTweet(@PathVariable String id) {

        Tweet existingTweet = tweetService.getTweetById(Long.valueOf(id));

        String userIdByToken = keycloakService.extractUserId();
        if (!userIdByToken.equals(existingTweet.getUserId())) {
            throw new NotValidOwnerException(existingTweet.getUserId(), "#NotValidOwner");
        }

        tweetService.delete(existingTweet);
    }

    @PostMapping("v1/tweet/comments")
    @ResponseStatus(HttpStatus.OK)
    public TweetGetDto addComment(@Valid @RequestBody CommentDto commentDto) {

        String userIdByToken = keycloakService.extractUserId();

        Comment comment = ObjectMapperUtils.map(commentDto, Comment.class);
        comment.setUserId(userIdByToken);

        Tweet tweet = tweetService.getTweetById(commentDto.getTweetId());

        tweetService.addComment(tweet, comment);

        Tweet returnedTweet = tweetService.getTweetById(commentDto.getTweetId());
        return ObjectMapperUtils.map(returnedTweet, TweetGetDto.class);
    }

    @DeleteMapping("v1/tweet/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable String id) {

        String userIdByToken = keycloakService.extractUserId();
        Comment comment = commentService.getCommentById(Long.valueOf(id));

        if (!userIdByToken.equals(comment.getUserId())) {
            throw new NotValidOwnerException(comment.getUserId(), "#NotValidOwner");
        }

        commentService.delete(comment);

    }


}
