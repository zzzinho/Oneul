package com.example.oneul.DTO;

import com.example.oneul.model.UserEntity;

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
