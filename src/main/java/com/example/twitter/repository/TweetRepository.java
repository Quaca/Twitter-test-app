package com.example.twitter.repository;

import com.example.twitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<List<Tweet>> findTweetsByUserIdEquals(String userId);
}
