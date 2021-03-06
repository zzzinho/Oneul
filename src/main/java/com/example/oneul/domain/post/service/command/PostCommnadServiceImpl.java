package com.example.oneul.domain.post.service.command;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.global.error.exception.ExpiredSessionException;
import com.example.oneul.global.error.exception.NotFoundException;
import com.example.oneul.infra.kafka.KafkaPublisher;
import com.example.oneul.infra.kafka.Type;
import com.example.oneul.infra.kafka.service.MessageQueueFactory;

@Service
@Transactional
public class PostCommnadServiceImpl implements PostCommandService{
    private final Logger log = LoggerFactory.getLogger(PostCommnadServiceImpl.class);

    private final PostCommandRepository postCommandRepository;
    private final MessageQueueFactory messageQueueFactory;
    private final KafkaPublisher kafkaPublisher;
    
    public PostCommnadServiceImpl(PostCommandRepository postCommandRepository, KafkaPublisher kafkaPublisher, MessageQueueFactory messageQueueFactory){
        this.postCommandRepository = postCommandRepository;
        this.kafkaPublisher = kafkaPublisher;
        this.messageQueueFactory = messageQueueFactory;
    }
    
    @Override
    public Post createPost(Post post, HttpSession httpSession){
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(userEntity == null){
            throw new ExpiredSessionException("만료된 세션");
        }
        
        LocalDateTime createdAt = LocalDateTime.now();
        
        Post postEntity =  postCommandRepository.save(
            Post.builder()
                .content(post.getContent())
                .createdAt(createdAt)
                .expiredAt(createdAt.plusHours(24))
                .writer(userEntity)
                .build());

        Type type = Type.valueOf("INSERT");
        messageQueueFactory.getTye(type).apply(postEntity);
        
        log.info("user: " + userEntity.toString() + " create " + postEntity.toString());
        return postEntity;
    }

    @Override
    public Post updatePost(Long id, Post post, HttpSession httpSession){ 
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(userEntity == null){
            throw new ExpiredSessionException("만료된 세션");
        }
        Post postEntity = postCommandRepository.findByIdAndWriter(id, userEntity).orElseThrow(() -> new NotFoundException(id + " post not found"));
        
        postEntity.setConent(post.getContent());
        postEntity = postCommandRepository.save(postEntity);

        Type type = Type.valueOf("UPDATE");
        messageQueueFactory.getTye(type).apply(postEntity);

        log.info(postEntity.toString() + " is updated");

        return postEntity;
    }

    @Override
    public void deletePost(Long id, HttpSession httpSession){
        UserEntity userEntity = (UserEntity)httpSession.getAttribute("user");
        if(userEntity == null){
            throw new ExpiredSessionException("만료된 세션");
        }
        Post postEntity = postCommandRepository.findByIdAndWriter(id, userEntity).orElseThrow(() -> new NotFoundException(id + " post not found"));
        postCommandRepository.deleteByIdAndWriter(id, userEntity);
        
        Type type = Type.valueOf("DELETE");
        messageQueueFactory.getTye(type).apply(postEntity);
    
        log.info("post " + id + " is deleted");
    }
}