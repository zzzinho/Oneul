package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.LoginDTO;
import com.example.oneul.domain.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userCommandService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    protected MockHttpSession httpSession;

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }
    
    @Test
    public void signUpTest(){
        LoginDTO loginDTO = new LoginDTO("zzzinho", "password");
        UserEntity user = userCommandService.signUp(loginDTO.toEntity());
        assertEquals(loginDTO.getUsername(), user.getUsername());
        assertEquals(true, passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()));
    }
}
