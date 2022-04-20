package com.example.oneul.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class UserEntity {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;

    public Long getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public UserEntity(Long id, String username, String password, LocalDateTime createdAt){
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        return "userEntity["
            + "id: " + this.id
            + ", username: " + this.username
            + ", createdAt: " + this.createdAt
            + "]";
    }
    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private LocalDateTime createdAt;

        public UserEntity build() {
            return new UserEntity(
                id, 
                username, 
                password, 
                createdAt);
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }
    }
}
