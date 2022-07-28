package com.example.twitter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Date publishedAt;

    private Date updatedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TWEET_ID", nullable = false)
    private List<Comment> comments;


    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

}
