package com.example.oneul.model;

import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Post {
    @Id @GeneratedValue
    private Long id;
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String content;
    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity writer;

    public Post(Long id, LocalDateTime createdAt, String content, UserEntity writer){
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.writer = writer;
    }

    public Long getId(){
        return this.id;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public String getContent(){
        return this.content;
    }

    public UserEntity getWriter(){
        return this.writer;
    }
    
    @Override
    public String toString(){
        return "Post["
            + "id: " + this.id
            + ", createdAt: " + this.createdAt
            + ", content: " + this.content
            + "writer: " + this.writer.getId()
            + "]";
    }

    public static class Builder {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private UserEntity writer;

        public Post build(){
            return new Post(
                id, 
                createdAt, 
                content, 
                writer);
        }
        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Builder writer(UserEntity writer){
            this.writer = writer;
            return this;
        }
    }
}
