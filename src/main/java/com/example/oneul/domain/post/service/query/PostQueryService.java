package com.example.oneul.domain.post.service.query;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostQueryService {
    Page<Post> findAll(PageRequest pageRequest);
    Page<Post> findByWriter(Long writerId, PageRequest pageRequest);
    Post insertPost(Post post);
}
