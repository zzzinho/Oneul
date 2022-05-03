package com.example.oneul.domain.post.dao;

import com.example.oneul.domain.post.domain.Post;

public interface PostCommandRepository{
    Post save(Post post);
    Post update(Post post);
    void delete(Long id);
}
