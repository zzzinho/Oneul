package com.example.oneul.domain.post.service.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.oneul.domain.post.domain.PostDocument;

public interface PostQueryService {
    Page<PostDocument> findAll(PageRequest pageRequest);
    Page<PostDocument> findByWriter(Long writerId, PageRequest pageRequest);
    PostDocument insertPost(PostDocument post);
}
