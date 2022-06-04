package com.example.oneul.domain.post.dao.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.oneul.domain.post.domain.PostDocument;

public interface PostQueryRepository extends MongoRepository<PostDocument, Long> {
    Page<PostDocument> findAll(Pageable pageable);
}
