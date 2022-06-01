package com.example.oneul.domain.post.dao.query;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostQueryRepository extends MongoRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}
