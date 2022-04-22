package com.example.oneul.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.example.oneul.service.command.UserCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    private UserCommandService userCommandService;
    private MockHttpSession httpSession = new MockHttpSession();

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userCommandService))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
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
            post("/user/")
                .contentType(MediaType.APPLICATION_JSON)  
                .session(httpSession)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        );
        
        actions.andExpectAll(status().isOk());
    }
}
