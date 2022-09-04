package com.example.twitter.model.followership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowershipId implements Serializable {
    private String fromId;
    private String toId;
}
