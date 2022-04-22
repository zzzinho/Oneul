package com.example.oneul.service.command;

import javax.servlet.http.HttpSession;

import com.example.oneul.model.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {
    UserEntity signUp(UserEntity userEntity, HttpSession httpSession);
}
