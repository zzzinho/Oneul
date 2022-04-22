package com.example.oneul.service.command;

import com.example.oneul.model.UserEntity;

import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {
    UserEntity signUp(UserEntity userEntity);
}
