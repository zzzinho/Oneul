package com.example.oneul.domain.user.dto;

import javax.validation.constraints.NotBlank;

import com.example.oneul.domain.user.domain.UserEntity;

import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }

    public LoginDTO() {}

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
