package com.example.oneul.domain.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.oneul.domain.user.domain.UserEntity;

@Service
public interface UserService {
    UserEntity signUp(UserEntity userEntity);
    UserEntity login(UserEntity userEntity, HttpSession httpSession);
    void logout(HttpSession httpSession);
}
