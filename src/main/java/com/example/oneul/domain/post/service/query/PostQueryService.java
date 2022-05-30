package com.example.oneul.domain.post.service.query;

import java.util.List;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostQueryService {
    List<Post> test();
    Page<Post> findAll(PageRequest pageRequest);
    Page<Post> findByWriter(Long writerId, PageRequest pageRequest);
}
