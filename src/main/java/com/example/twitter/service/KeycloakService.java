package com.example.twitter.service;

import com.example.twitter.exception.NoResourceException;
import com.example.twitter.model.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.IDToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.Arrays;

@Service
public class KeycloakService {

    private static final String SERVER_URL = "http://localhost:8180/auth";
    private static final String REALM = "SpringBootRealm";
    private static final String CLIENT_ID = "login-app";
    private static final String CLIENT_SECRET = "e27g1FHwqN0q2taZ8o7KCJuKdEqVbY6e";
    private Keycloak keycloak = null;
    private Keycloak keycloakLogin = null;

    public Keycloak getKeycloakInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder
                    .builder()
                    .serverUrl(SERVER_URL)
                    .realm(REALM)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .clientId(CLIENT_ID)
                    .clientSecret(CLIENT_SECRET)
                    .build();
        }
        return keycloak;
    }


    public KeycloakBuilder getKeycloakLoginInstance(String username, String password) {
        return KeycloakBuilder
                .builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(username)
                .password(password);
    }

    public User registerNewUser(User user) {

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getSurname());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setCredentials(Arrays.asList(credentialRepresentation));
        userRepresentation.setEnabled(true);

        UsersResource usersResource = getKeycloakInstance().realm(REALM).users();
        Response response = usersResource.create(userRepresentation);

        return user;

    }

    public AccessTokenResponse loginUser(User user) {

        AccessTokenResponse response = getKeycloakLoginInstance(user.getUsername(), user.getPassword()).build().tokenManager().getAccessToken();

        return response;

    }

    public String loginUserStringToken(User user) {

        String response = getKeycloakLoginInstance(user.getUsername(), user.getPassword()).build().tokenManager().getAccessTokenString();

        return response;

    }

    public UserRepresentation findUserById(String userId) {
        KeycloakAuthenticationToken authentication =
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getPrincipal();

        if (principal instanceof KeycloakPrincipal) {

            RealmResource realmResource = getKeycloakInstance().realm(REALM);
            try {
                UserRepresentation userRepresentation = realmResource.users().get(userId).toRepresentation();
                return userRepresentation;
            } catch (NotFoundException e) {
                throw new NoResourceException(userId,"#UserNotExisting");
            }

        } else {
            throw new NoResourceException(userId);
        }
    }

    public String extractUserId() {
        KeycloakAuthenticationToken authentication =
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getPrincipal();
        String userIdByToken = "";
        if (principal instanceof KeycloakPrincipal) {

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext()
                    .getToken();

            userIdByToken = token.getSubject();
        }
        return userIdByToken;
    }

    public User extractUserFromToken() {
        KeycloakAuthenticationToken authentication =
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getPrincipal();
        User user = new User();

        if (principal instanceof KeycloakPrincipal) {

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext()
                    .getToken();

            user.setId(token.getSubject());
            user.setName(token.getGivenName());
            user.setSurname(token.getFamilyName());
            user.setUsername(token.getPreferredUsername());
        }
        return user;
    }
}
