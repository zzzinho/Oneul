package com.example.oneul.domain.post.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = @Index(name = "i_post", columnList="createdAt"))
public class Post implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime deletedAt;
    @Column(nullable = false)
    private String content;
    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserEntity writer;
    
    public Post() {}

    public Post(Long id, LocalDateTime createdAt, LocalDateTime expiredAt, String content, UserEntity writer){
        this.id = id;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.deletedAt = null;
        this.content = content;
        this.writer = writer;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return this.expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt){
        this.expiredAt = expiredAt;
    }

    public LocalDateTime getDeletedAt(){
        return this.deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public String getContent(){
        return this.content;
    }

    public void setConent(String content){
        this.content = content;
    }

    public UserEntity getWriter(){
        return this.writer;
    }
    
    public void setWriter(UserEntity writer){
        this.writer = writer;
    }
    
    @Override
    public boolean equals(Object object){
        if(this == object) {
            return true;
        }
        if(object == null || getClass() != object.getClass()) {
            return false;
        }
        Post that = (Post) object;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
    
    @Override
    public String toString(){
        return "Post["
            + "id: " + this.id
            + ", createdAt: " + this.createdAt
            + ", expiredAt: " + this.expiredAt
            + ", deletedAt; " + this.deletedAt
            + ", content: " + this.content
            + "writer: " + this.writer.getId()
            + "]";
    }

    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime expiredAt;
        private UserEntity writer;

        public Post build(){
            return new Post(
                id, 
                createdAt, 
                expiredAt,
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

        public Builder expiredAt(LocalDateTime expiredAt){
            this.expiredAt = expiredAt;
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
