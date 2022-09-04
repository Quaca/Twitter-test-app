package com.example.twitter.users;

import com.example.twitter.model.User;
import com.example.twitter.service.KeycloakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles({ "local" })
@ContextConfiguration
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    private UserRequestBuilder userRequestBuilder;

    @Autowired
    private KeycloakService keycloakService;

    @BeforeEach
    void configureSystemUnderTest() {
        userRequestBuilder = new UserRequestBuilder(keycloakService, mvc);

    }

    @Test
    @Transactional
    public void getUserById_Successful_ValidId() throws Exception {
        userRequestBuilder.findUserById("c43e76ca-9e56-4b96-b6db-0ff6bb083ee8").andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getUserById_Unsuccessful_NotExistingId() throws Exception {
        userRequestBuilder.findUserById("1")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("#UserNotExisting"));
    }

    @Test
    @Transactional
    public void createUser_Successful_UserObjectPassed() throws Exception {
        String json = "{\"name\":\"Steve\"," +
                "\"surname\":\"Jobss\"," +
                "\"email\":\"test@gmail.com\"," +
                "\"username\":\"sJobss\"," +
                "\"password\":\"rootpass16\"," +
                "\"matchingPassword\":\"rootpass16\"," +
                "\"country\":\"USA\"}";
        userRequestBuilder.createUser(json)
                .andExpect(status().isOk());
    }
//
//    @Test
//    @Transactional
//    public void updateUser_Successful_UserObjectPassed() throws Exception {
//        String json = "{\"name\":\"Steve\",\"surname\":\"Jobs\",\"country\":\"USA-America\"}";
//        userRequestBuilder.updateUser(json)
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Transactional
//    public void deleteUserById_Successful_Id() throws Exception {
//        userRequestBuilder.deleteUserById(78L)
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Transactional
//    public void deleteUserById_Unsuccessful_Id() throws Exception {
//        userRequestBuilder.deleteUserById(100453L)
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.errorCode").value("#UserNotExisting"));
//    }


}
