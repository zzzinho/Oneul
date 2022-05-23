package com.example.oneul.domain.post.service.command;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.post.dao.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.global.error.exception.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostCommnadServiceImpl implements PostCommandService{
    private final PostCommandRepository postCommandRepository;

    private final Logger log = LoggerFactory.getLogger(PostCommnadServiceImpl.class);

    public PostCommnadServiceImpl(PostCommandRepository postCommandRepository){
        this.postCommandRepository = postCommandRepository;
    }
    
    @Override
    public Post createPost(Post post, HttpSession httpSession){
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        
        Post postEntity =  postCommandRepository.save(
            Post.builder()
                .content(post.getContent())
                .writer(userEntity)
                .build());

        log.info("user: " + userEntity.toString() + " create " + post.toString());
        return postEntity;
    }

    @Override
    public Post updatePost(Long id, Post post, HttpSession httpSession){  
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        Post postEntity = postCommandRepository.findByIdAndWriter(id, userEntity).orElseThrow(() -> new NotFoundException(id + " post not found"));
        postEntity.setConent(post.getContent());
        postEntity = postCommandRepository.save(postEntity);
        log.info(postEntity.toString() + " is updated");

        return postEntity;
    }

    @Override
    public void deletePost(Long id, HttpSession httpSession){
        postCommandRepository.deleteById(id);
        log.info("post " + id + " is deleted");
    }
}
