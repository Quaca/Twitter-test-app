package com.example.twitter.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private String country;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "followers",
            joinColumns = { @JoinColumn(name = "to_id") },
            inverseJoinColumns = { @JoinColumn(name = "from_id") }
    )
    private Set<User> followers = new HashSet<>();


    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();


    public void addFollower(User user){
        this.followers.add(user);
    }
    public void follow(User user){
        this.following.add(user);
        user.followers.add(this);
    }

    public void unfollow(User user){
        this.following.remove(user);
        user.followers.remove(this);
    }


//    @Override
//    public String toString() {
//        String result = getId().toString() + "," + getName() + "," + getSurname() + "," + getCountry();
//        return result;
//    }

//    @ManyToMany(mappedBy = "to", fetch = FetchType.LAZY)
//    @JoinTable(name="followers")
//    private Set<User> followers;
//
//    @ManyToMany(mappedBy="from", fetch = FetchType.LAZY)
//    @JoinTable(name="followers")
//    private Set<User> following;


//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToMany(mappedBy = "to")
//    Set<User> followers = new HashSet<>();
//
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToMany(mappedBy = "from")
//    Set<User> following = new HashSet<>();
}
