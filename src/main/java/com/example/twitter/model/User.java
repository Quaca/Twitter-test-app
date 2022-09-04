package com.example.twitter.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {


    private String id;

    private String name;

    private String surname;

    private String country;

    private String email;

    private String username;

    private String password;

    private List<String> followers;

    private List<String> following;

    public void addFollowing(String toId) {
        if (!following.contains(toId)) {
            following.add(toId);
        }
    }

}
