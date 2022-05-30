package com.example.oneul.domain.post.dto;

import javax.validation.constraints.NotBlank;

import com.example.oneul.domain.post.domain.Post;

public class PostDTO {
    @NotBlank
    private String content; 

    public PostDTO() {}

    public PostDTO(String content){
        this.content = content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public Post toEntity() {
        return Post.builder()
            .content(content)
            .build();
    }
}
