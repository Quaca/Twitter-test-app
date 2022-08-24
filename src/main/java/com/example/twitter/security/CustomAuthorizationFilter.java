package com.example.twitter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.twitter.context.UserContext;
import com.example.twitter.context.UserContextHolder;
import com.example.twitter.model.User;
import com.example.twitter.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    UserService userService;

    public CustomAuthorizationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")
                || request.getServletPath().equals("/registrationConfirm")
                || request.getServletPath().equals("/resetPassword")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("authorization");
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    User user = userService.getUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    User userToReturn = null;
                    Object principal = authenticationToken.getPrincipal();
                    if(principal instanceof User){
                        userToReturn = (User) principal;
                    }

                    if (userToReturn != null){
                        UserContext userContext = new UserContext();
                        userContext.setUser(user);
                        UserContextHolder.set(userContext);
                    }

                    filterChain.doFilter(request, response);
                } catch (Exception exception){
                    System.out.println("Cannot authorize");
                }
            } else {
                filterChain.doFilter(request, response);
            }

        }
    }
}
