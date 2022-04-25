package com.example.oneul.domain.user.service;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserEntity signUp(UserEntity userEntity, HttpSession httpSession);
    UserEntity login(UserEntity userEntity , HttpSession httpSession);
    void logout(UserEntity userEntity, HttpSession httpSession);
}
