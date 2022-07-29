package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
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
    public User getUserById(Long id) throws NoResourceException {
        return userRepository.findById(id).orElseThrow(() -> new NoResourceException(String.valueOf(id), "There is no user with id " + id, "#UserNotExisting"));
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {

        User existingUser = getUserById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setCountry(user.getCountry());
        existingUser.setTweets(user.getTweets());

        userRepository.save(existingUser);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

}
