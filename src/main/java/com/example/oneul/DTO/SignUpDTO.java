package com.example.oneul.DTO;

import com.example.oneul.model.UserEntity;

import lombok.Getter;

@Getter
public class SignUpDTO {
    private String username;
    private String password1;
    private String password2;

    public void setUsername(String username ){
        this.username = username;
    }

    public void setPassword1(String password1){
        this.password1 = password1;
    }

    public void setPassword2(String password2){
        this.password2 = password2;
    }

    public SignUpDTO() {}
    
    public SignUpDTO(String username, String password1, String password2){
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
            .username(this.username)
            .password(this.password1)
            .build();
    }
}
