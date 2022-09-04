package com.example.twitter.repository;

import com.example.twitter.model.followership.Followership;
import com.example.twitter.model.followership.FollowershipId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowershipRepository extends JpaRepository<Followership, FollowershipId> {
    Optional<Followership> findByFromIdAndToId(String fromId, String toId);
    List<Followership> findByFromId(String fromId);
}
