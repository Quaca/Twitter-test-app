package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.exception.ResourceAlreadyExistingException;
import com.example.twitter.model.User;
import com.example.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoResourceException(String.valueOf(id), "There is no user with id " + id, "#UserNotExisting"));
    }

    @Transactional
    public User save(User user) {
        User retrievedUser = userRepository.save(user);
        return retrievedUser;
    }

    @Transactional
    public User update(User user) {

        User existingUser = getUserById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setCountry(user.getCountry());
        existingUser.setTweets(user.getTweets());

        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }


    public User followUser(Long fromUserId, Long toUserId) {

        User fromUser = getUserById(fromUserId);
        User toUser = getUserById(toUserId);

        if(fromUser.getFollowing().contains(toUser)){
            throw new ResourceAlreadyExistingException("User already follows the specified user.","#ResourceAlreadyExisting");
        }
        fromUser.follow(toUser);
        return userRepository.save(fromUser);
    }


    public User unfollowUser(Long fromUserId, Long toUserId){
        User fromUser = getUserById(fromUserId);
        User toUser = getUserById(toUserId);

        if(!fromUser.getFollowing().contains(toUser)){
            throw new NoResourceException( "User does not follow the specified user", "#FollowershipNotExisting");
        }
        fromUser.unfollow(toUser);
        return userRepository.save(fromUser);
    }

}
