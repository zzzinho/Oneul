package com.example.oneul.domain.post.service.command;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.stereotype.Service;

@Service
public interface PostCommandService {
    Post createPost(Post post, HttpSession httpSession);
    Post updatePost(Long id, Post post, HttpSession httpSession);
    void deletePost(Long id, HttpSession httpSession);
}
