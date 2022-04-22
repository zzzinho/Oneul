package com.example.oneul.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class UserEntity {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Post> posts;
    
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

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public UserEntity(Long id, String username, String password, LocalDateTime createdAt){
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public UserEntity(UserEntity user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
    }
    
    @Override
    public String toString(){
        return "userEntity["
            + "id: " + this.id
            + ", username: " + this.username
            + ", createdAt: " + this.createdAt
            + "]";
    }
    public static Builder builder() {
        return new Builder();
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
