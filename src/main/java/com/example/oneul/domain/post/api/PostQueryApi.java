package com.example.oneul.domain.post.api;

import java.util.List;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.post.service.query.PostQueryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping(value = "/post")
public class PostQueryApi {
    private final PostQueryService postQueryService;
    
    public PostQueryApi(PostQueryService postQueryService){
        this.postQueryService = postQueryService;
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public Page<Post> findAll(@RequestParam("page") Integer page) {
        Page<Post> posts = postQueryService.findAll(PageRequest.of(page, 10));
        return posts;
    }

    @RequestMapping(value="/writer/{writerId}", method=RequestMethod.GET)
    public Page<Post> findAllByWriter(@PathVariable Long writerId, @RequestParam("page") Integer page) {
        Page<Post> posts = postQueryService.findByWriter(writerId, PageRequest.of(page, 10));
        return posts;
    }

    @GetMapping(value="/test")
    public List<Post> test() {
        return postQueryService.test();
    }
    
}
