package com.example.oneul.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.oneul.domain.post.dao.PostCommandRepository;
import com.example.oneul.domain.post.dao.PostQueryRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.global.config.JdbcConfig;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
public class PostCommandRepositoryTest {
    private PostCommandRepository postCommandRepository;
    @Autowired private PostQueryRepository postQueryRepository;
    @Autowired private UserRepository userRepository;

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }
    
    @BeforeAll
    public void init(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(JdbcConfig.class);
        postCommandRepository = ac.getBean("postCommandRepository", PostCommandRepository.class);
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

        post = postCommandRepository.save(post);
        Post createdPost = postQueryRepository.findById(post.getId()).orElse(new Post());
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
        postCommandRepository.delete(createdPost);
    }

    @Test
    public void deleteByIdTest(){
        UserEntity testUser = createTestUser();

        Post post = Post.builder()
                        .content("test content")
                        .writer(testUser)
                        .build();

        Post createdPost = postCommandRepository.save(post);   
        System.out.println("post id: " + post.getId());
        postCommandRepository.deleteById(createdPost.getId());
    }
}
