package com.example.oneul.service;

import com.example.oneul.DTO.LoginDTO;
import com.example.oneul.model.UserEntity;
import com.example.oneul.service.command.UserCommandService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserCommandServiceTest {
    @Autowired
    private UserCommandService userCommandService;
    
    @Test
    public void signUpTest(){
        LoginDTO loginDTO = new LoginDTO("zzzinho", "password");
        UserEntity user = userCommandService.signUp(loginDTO.toEntity());
    }
}
