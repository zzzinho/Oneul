package com.example.oneul.domain.post.dao;

import java.util.Optional;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostQueryRepository extends CrudRepository<Post, Long>{
    Optional<Post> findById(Long id);
    Iterable<Post> findAll();
}
