package com.example.oneul.domain.post.dao;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostCommandRepository extends CrudRepository<Post, Long> {
    Post save(Post post);
    void deleteById(Long id);
    void delete(Post post);
}
