package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.post.service.command.PostCommandService;
import com.example.oneul.domain.post.service.command.PostCommnadServiceImpl;
import com.example.oneul.infra.kafka.KafkaPublisher;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PostCommandSerivceTest {
    private PostCommandService postCommandService;
    @Mock private PostCommandRepository postCommandRepository;
    @Mock private KafkaPublisher kafkaPublisher;
    protected MockHttpSession httpSession;

    @BeforeEach
    public void setUp() throws Exception {
        postCommandService = new PostCommnadServiceImpl(postCommandRepository, kafkaPublisher);
    }

    @Test
    public void createPostTest() throws Exception {
        // given
        Long mockPostId = 1L;
        Post post = mockPost(mockPostId);
        ReflectionTestUtils.setField(post, "id", mockPostId);

        // mocking
        given(postCommandRepository.save(post)).willReturn(post);
        given(postCommandRepository.findById(mockPostId)).willReturn(Optional.ofNullable(post));

        // when
        Post createdPost = postCommandService.createPost(post, httpSession);

        // then
        assertEquals(post.getContent(), createdPost.getContent());
    }

    private Post mockPost(Long id) {
        return Post.builder().content("mocking post")
                            .build();
    }
}
