package com.example.oneul.domain.post.dao.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.domain.UserEntity;

public interface PostCommandRepository extends JpaRepository<Post, Long> {
    Post save(Post post);
    Optional<Post> findByIdAndWriter(Long id, UserEntity writer);
    void deleteByIdAndWriter(Long id, UserEntity writer);
    void deleteById(Long id);
    void delete(Post post);

    List<Post> findAllByExpiredAtLessThanAndDeletedAtIsNull(LocalDateTime expiredAt);  
}