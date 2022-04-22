package com.example.oneul.domain.user.dto;

import com.example.oneul.domain.user.domain.UserEntity;

import lombok.Getter;

@Getter
public class LoginDTO {
    private String username;
    private String password;
    
    public LoginDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public UserEntity toEntity(){
        return UserEntity.builder()
            .username(username)
            .password(password)
            .build();
    }
}
