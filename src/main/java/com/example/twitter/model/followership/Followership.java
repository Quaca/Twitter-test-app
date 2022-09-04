package com.example.twitter.model.followership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "followers")
@IdClass(FollowershipId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Followership {
    @Id
    private String fromId;
    @Id
    private String toId;

    public Followership(FollowershipId followershipId){
        this.fromId = followershipId.getFromId();
        this.toId = followershipId.getToId();
    }

//    @EmbeddedId
//    private FollowershipId followershipId;
}
