package com.example.twitter.context;


import com.example.twitter.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class UserContext {
    private User user;
    private String currentUserId;
}
