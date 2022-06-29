package com.example.oneul.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.global.common.Code;
import com.example.oneul.global.common.Response;

public class UserInfo {
    @NotNull
    private Long id;
    @NotBlank
    private String username;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public UserInfo() {}

    public UserInfo(Long id, String username){
        this.id = id;
        this.username = username;
    }

    public static class UserResponse extends Response {
        private UserInfo body;
        public UserResponse(Code code, String message, UserEntity user){
            super(code, message);
            this.body = new UserInfo(user.getId(), user.getUsername());
        }

        public UserInfo getUserInfo(){
            return this.body;
        }
    }
}
