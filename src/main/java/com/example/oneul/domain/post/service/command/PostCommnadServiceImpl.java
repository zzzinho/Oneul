package com.example.oneul.domain.post.service.command;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
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
    private final Logger log = LoggerFactory.getLogger(PostCommnadServiceImpl.class);

    private final PostCommandRepository postCommandRepository;

    public PostCommnadServiceImpl(PostCommandRepository postCommandRepository){
        this.postCommandRepository = postCommandRepository;
    }
    
    @Override
    public Post createPost(Post post, HttpSession httpSession){
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        LocalDateTime createdAt = LocalDateTime.now();

        Post postEntity =  postCommandRepository.save(
            Post.builder()
                .content(post.getContent())
                .createdAt(createdAt)
                .expiredAt(createdAt.plusHours(24))
                .writer(userEntity)
                .build());

        log.info("user: " + userEntity.toString() + " create " + postEntity.toString());
        return postEntity;
    }

    @Override
    public Post updatePost(Long id, Post post, HttpSession httpSession){ 
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        // TODO: 적절한 방법인지 확인하기
        Post postEntity = postCommandRepository.findByIdAndWriter(id, userEntity).orElseThrow(() -> new NotFoundException(id + " post not found"));
        postEntity.setConent(post.getContent());
        postEntity = postCommandRepository.save(postEntity);
        log.info(postEntity.toString() + " is updated");

        return postEntity;
    }

    @Override
    public void deletePost(Long id, HttpSession httpSession){
        // TODO: 이 때 세션이 만기되면 어떡함
        UserEntity userEntity = (UserEntity)httpSession.getAttribute("user");
        postCommandRepository.deleteByIdAndWriter(id, userEntity);
        log.info("post " + id + " is deleted");
    }
}
