package com.example.oneul.infra.kafka.service;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.Type;

public interface MessageQueueService {
    Type getMessageType();
    PostMessage transaction(Post post);
}
