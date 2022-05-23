package com.example.oneul.domain.post.dao;

import java.util.Optional;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostQueryRepository extends JpaRepository<Post, Long>{
    Optional<Post> findById(Long id);
    Page<Post> findAll(Pageable pageable);
}
