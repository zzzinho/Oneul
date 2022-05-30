package com.example.oneul.domain.post.api;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.post.dto.PostDTO;
import com.example.oneul.domain.post.service.command.PostCommandService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/post")
public class PostCommandApi {
    private final PostCommandService postCommandService;
    
    public PostCommandApi(PostCommandService postCommandService){
        this.postCommandService = postCommandService;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public Post createPost(HttpSession httpSession, @RequestBody PostDTO postDTO) {
        Post post = postCommandService.createPost(postDTO.toEntity(), httpSession);
        return post;
    }
    
    @RequestMapping(value="/{postId}/", method=RequestMethod.PUT)
    public Post updatePost(HttpSession httpSession, @RequestBody PostDTO postDTO, @PathVariable Long postId) {
        Post post = postCommandService.updatePost(postId, postDTO.toEntity(), httpSession);
        return post;
    }
    
}
