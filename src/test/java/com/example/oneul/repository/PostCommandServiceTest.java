package com.example.oneul.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.oneul.domain.post.dao.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
public class PostCommandServiceTest {
    @Autowired private PostCommandRepository postCommandRepository;
    @Autowired private UserRepository userRepository;

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }
    
    private UserEntity createTestUser(){
        return userRepository.save(UserEntity.builder()
                                             .username("test user")
                                             .password("test pw")
                                             .build());
    }

    @Test
    public void saveTest(){
        UserEntity testUser = createTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);
        
        assertEquals(false, createdPost.getId() == null);
        assertEquals(post, createdPost);
    }

    @Test
    public void updateTest(){
        UserEntity testUser = createTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);
        createdPost.setConent("updated");

        Post updatedPost = postCommandRepository.update(createdPost);
        
        assertEquals(createdPost, updatedPost);;
    }

    // TODO: 왜 Transaction이 없다고 할까
    @Test
    public void deleteTest(){
        UserEntity testUser = createTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);   
        Long id = createdPost.getId();
        postCommandRepository.delete(id);
    }
}
