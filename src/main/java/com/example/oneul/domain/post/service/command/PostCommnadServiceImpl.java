package com.example.oneul.domain.post.service.command;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
import com.example.oneul.domain.post.dao.query.PostQueryRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.global.error.exception.NotFoundException;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.KafkaPublisher;

@Service
@Transactional
public class PostCommnadServiceImpl implements PostCommandService{
    private final Logger log = LoggerFactory.getLogger(PostCommnadServiceImpl.class);

    private final PostCommandRepository postCommandRepository;
    private final PostQueryRepository postQueryRepository;
    private final KafkaPublisher kafkaPublisher;
    
    public PostCommnadServiceImpl(PostCommandRepository postCommandRepository, PostQueryRepository postQueryRepository, KafkaPublisher kafkaPublisher){
        this.postCommandRepository = postCommandRepository;
        this.postQueryRepository = postQueryRepository;
        this.kafkaPublisher = kafkaPublisher;
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

        kafkaPublisher.sendMessage(
            "post", 
            new PostMessage(
                "INSERT",
                postEntity.getId(), 
                postEntity.getCreatedAt(), 
                postEntity.getContent(), 
                postEntity.getWriter().getUsername()
            )
        );
    
        log.info("user: " + userEntity.toString() + " create " + postEntity.toString());
        return postEntity;
    }

    @Override
    public Post updatePost(Long id, Post post, HttpSession httpSession){ 
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        Post postEntity = postCommandRepository.findByIdAndWriter(id, userEntity).orElseThrow(() -> new NotFoundException(id + " post not found"));
        
        postEntity.setConent(post.getContent());
        postEntity = postCommandRepository.save(postEntity);

        kafkaPublisher.sendMessage(
            "post", 
            new PostMessage(
                "UPDATE",
                postEntity.getId(), 
                postEntity.getCreatedAt(), 
                postEntity.getContent(), 
                postEntity.getWriter().getUsername()
            )
        );

        log.info(postEntity.toString() + " is updated");

        return postEntity;
    }

    @Override
    public void deletePost(Long id, HttpSession httpSession){
        // TODO: 이 때 세션이 만기되면 어떡함
        UserEntity userEntity = (UserEntity)httpSession.getAttribute("user");
        
        postCommandRepository.deleteByIdAndWriter(id, userEntity);

        kafkaPublisher.sendMessage(
            "post", 
            PostMessage.builder()
                .type("DELETE")
                .id(id)
                .build());
    
        log.info("post " + id + " is deleted");
    }
}