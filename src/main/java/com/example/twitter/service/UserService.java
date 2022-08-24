package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.exception.ResourceAlreadyExistingException;
import com.example.twitter.exception.UserAlreadyExistException;
import com.example.twitter.model.PasswordResetToken;
import com.example.twitter.model.Role;
import com.example.twitter.model.User;
import com.example.twitter.model.VerificationToken;
import com.example.twitter.repository.PasswordResetTokenRepository;
import com.example.twitter.repository.RoleRepository;
import com.example.twitter.repository.TokenRepository;
import com.example.twitter.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {


    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private MessageSource messages;
    private Environment env;
    private JavaMailSender mailSender;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository,
                       PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder, MessageSource messages,
                       Environment env, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.messages = messages;
        this.env = env;
        this.mailSender = javaMailSender;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoResourceException(String.valueOf(id), "There is no user with id " + id, "#UserNotExisting"));
    }

    @Transactional(readOnly = true)
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(final String email) {
        final User user = userRepository.findByEmail(email);
        return user;
    }

    @Transactional
    public User save(User user) {

        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("Account with given email address already exists: " + user.getEmail(), "#UserAlreadyExisting");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User retrievedUser = userRepository.save(user);
        return retrievedUser;
    }


    @Transactional
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Transactional(readOnly = true)
    public VerificationToken getVerificationToken(final String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Transactional
    public User update(User user) {

        User existingUser = getUserById(user.getId());
        if (existingUser.getId() != user.getId()) {
            //TODO
            // throw exception
        }
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


    public User followUser(User fromUser, String toUserId) {

        User toUser = getUserById(Long.valueOf(toUserId));

        if (fromUser.getFollowing().contains(toUser)) {
            throw new ResourceAlreadyExistingException("User already follows the specified user.", "#ResourceAlreadyExisting");
        }
        fromUser.follow(toUser);
        return userRepository.save(fromUser);
    }


    public User unfollowUser(User fromUser, String toUserId) {
        User toUser = getUserById(Long.valueOf(toUserId));

        if (!fromUser.getFollowing().contains(toUser)) {
            throw new NoResourceException("User does not follow the specified user", "#FollowershipNotExisting");
        }
        fromUser.unfollow(toUser);
        return userRepository.save(fromUser);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(user.getRoles())
        );
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        return user;
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public User addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.addRole(role);
        return userRepository.save(user);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return authorities;
    }

    public void sendMail(final String contextPath, final Locale locale, final User user) {
        final String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(contextPath, locale, token, user));
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, "Successfully received password reset token. To reset you, please click on the below link.", locale);
//        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
//TODO
//        throw exception and catch in controller advice
        if (isTokenFound(passwordResetToken)) {
            return "invalidToken";
        } else if (isTokenExpired(passwordResetToken)) {
            return "expired";
        } else {
            return null;
        }
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    public User getUserByPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token).getUser();
    }

    public User changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }


}
