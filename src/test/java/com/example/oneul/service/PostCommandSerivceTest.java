package com.example.oneul.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.domain.post.service.command.PostCommandService;
import com.example.oneul.domain.post.service.command.PostCommnadServiceImpl;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.KafkaPublisher;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PostCommandSerivceTest {
    private PostCommandService postCommandService;
    @Mock private PostCommandRepository postCommandRepository;
    @Mock private KafkaPublisher kafkaPublisher;
    protected MockHttpSession httpSession;
    @Mock private UserEntity mockWriter;

    @BeforeEach
    public void setUp() throws Exception {
        postCommandService = new PostCommnadServiceImpl(postCommandRepository, kafkaPublisher);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("user", mockWriter);
    }

    @Test
    public void createPostTest() throws Exception {
        // given
        Long mockPostId = 1L;
        Post post = mockPostDTO();
        ReflectionTestUtils.setField(post, "id", mockPostId);

        // mocking
        Post createdMockPost = createdMockPost(mockWriter, post.getContent());
        ReflectionTestUtils.setField(createdMockPost, "id", mockPostId);
        given(postCommandRepository.save(any())).willReturn(createdMockPost);    

        // when
        Post createdPost = postCommandService.createPost(post, httpSession);

        // then
        assertEquals(post.getContent(), createdPost.getContent());
        ArgumentCaptor<PostMessage> captor = ArgumentCaptor.forClass(PostMessage.class);
        verify(kafkaPublisher).sendMessage(eq("post"), captor.capture());
        PostMessage postMessage = createPostMessage("INSERT", createdPost);
        assertEquals(postMessage, captor.getValue());
    }

    @Test
    public void updatePostTest() throws Exception {
        // given
        Long mockPostId = 1L;
        Post post = createdMockPost(mockWriter, "unmodified content");
        ReflectionTestUtils.setField(post, "id", mockPostId);
        
        // mocking
        given(postCommandRepository.findByIdAndWriter(eq(mockPostId), any()))
                                    .willReturn(Optional.ofNullable(post));
        ReflectionTestUtils.setField(post, "content", "modified content");
        given(postCommandRepository.save(post)).willReturn(post);

        // when
        Post updatedPost = postCommandService.updatePost(mockPostId, post, httpSession);

        // then
        assertEquals(post.getContent(), updatedPost.getContent());
        ArgumentCaptor<PostMessage> captor = ArgumentCaptor.forClass(PostMessage.class);
        verify(kafkaPublisher).sendMessage(eq("post"), captor.capture());
        PostMessage postMessage = createPostMessage("UPDATE", updatedPost);
        assertEquals(postMessage, captor.getValue());
    }

    private PostMessage createPostMessage(String type, Post post) {
        return new PostMessage(type, post.getId(), post.getCreatedAt(), post.getContent(), null);
    }

    private Post createdMockPost(UserEntity writer, String content) {
        LocalDateTime createdAt = LocalDateTime.now();
        return Post.builder().content(content)
                            .createdAt(createdAt)
                            .expiredAt(createdAt.plusHours(24))
                            .writer(writer)
                            .build();
    }

    private Post mockPostDTO() {
        return Post.builder().content("mocking post")
                            .build();
    }
}
