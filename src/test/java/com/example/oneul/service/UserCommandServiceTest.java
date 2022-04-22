package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.oneul.domain.user.command.UserCommandService;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.LoginDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserCommandServiceTest {
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    protected MockHttpSession httpSession;

    @Test
    public void signUpTest(){
        httpSession = new MockHttpSession();
        LoginDTO loginDTO = new LoginDTO("zzzinho", "password");
        UserEntity user = userCommandService.signUp(loginDTO.toEntity(), httpSession);
        assertEquals(loginDTO.getUsername(), user.getUsername());
        assertEquals(true, passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()));
    }
}
