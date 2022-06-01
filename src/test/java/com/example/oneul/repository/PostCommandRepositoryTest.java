package com.example.oneul.repository;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
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
public class PostCommandRepositoryTest {
    @Autowired private PostCommandRepository postCommandRepository;
    @Autowired private UserRepository userRepository;

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }

    private UserEntity getOrCreateTestUser(){
        return userRepository.findByUsername("test_user").orElseGet(() ->
                userRepository.save(UserEntity.builder()
                                             .username("test_user")
                                             .password("test pw")
                                             .build()));
    }

    @Test
    public void saveTest(){
        UserEntity testUser = getOrCreateTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        post = postCommandRepository.save(post);
        // Post createdPost = postQueryRepository.findById(post.getId()).orElse(new Post());
        // assertEquals(false, createdPost.getId() == null);
        // assertEquals(post, createdPost);
    }

    @Test
    public void updateTest(){
        UserEntity testUser = getOrCreateTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);
        createdPost.setConent("updated");

        Post updatedPost = postCommandRepository.save(createdPost);
        // Post savedPost = postQueryRepository.findById(updatedPost.getId()).orElse(null);
        // assertEquals(updatedPost, savedPost);
    }

    @Test
    public void deleteByIdTest(){
        UserEntity testUser = getOrCreateTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);   
        System.out.println("post id: " + post.getId());
        postCommandRepository.deleteById(createdPost.getId());

    }
}
