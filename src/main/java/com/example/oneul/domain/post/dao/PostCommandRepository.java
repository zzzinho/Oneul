package com.example.oneul.domain.post.dao;

import java.util.Optional;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.data.repository.CrudRepository;

public interface PostCommandRepository extends CrudRepository<Post, Long> {
    Post save(Post post);
    Optional<Post> findByIdAndWriter(Long id, UserEntity writer);
    void deleteById(Long id);
    void delete(Post post);
}