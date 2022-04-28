package com.example.oneul.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.example.oneul.domain.user.api.UserApi;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    private UserService userService;
    private MockHttpSession httpSession = new MockHttpSession();

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new UserApi(userService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }
    
    private UserEntity createTestUser(String username, String password){
        return userService.signUp(UserEntity.builder()
                                            .username(username)
                                            .password(password)
                                            .build());
    }

    @Test
    @DisplayName("signUp test")
    public void signUpTest() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "zzzinho");
        requestBody.put("password1", "password");
        requestBody.put("password2", "password");

        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestBody);

        final ResultActions actions = mvc.perform(
            post("/user/signup/")
                .contentType(MediaType.APPLICATION_JSON)  
                .session(httpSession)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        );
        
        actions.andExpectAll(status().isOk());
    }

    @Test
    @DisplayName("success login test")
    public void loginTest() throws Exception {
        UserEntity user = createTestUser("testuser", "testpw");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", user.getUsername());
        requestBody.put("password", "testpw");
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestBody);

        final ResultActions actions = mvc.perform(
            post("/user/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .session(httpSession)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)          
        );
        
        actions.andExpectAll(status().isOk());
    }

    @Test
    @DisplayName("fail login test")
    public void failLoginTest() throws Exception{
        UserEntity user = createTestUser("testuser", "testpw");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", user.getUsername());
        requestBody.put("password", "testw");
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestBody);

        final ResultActions actions = mvc.perform(
            post("/user/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .session(httpSession)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)          
        );
        
        actions.andExpectAll(status().isNotFound());   
    }

    @Test
    @DisplayName("logout test")
    public void logoutTest() throws Exception {
        
    }
}
