package com.example.oneul.domain.user.command;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {
    UserEntity signUp(UserEntity userEntity, HttpSession httpSession);
    UserEntity login(UserEntity userEntity , HttpSession httpSession);
    UserEntity logout(UserEntity userEntity, HttpSession httpSession);
}
