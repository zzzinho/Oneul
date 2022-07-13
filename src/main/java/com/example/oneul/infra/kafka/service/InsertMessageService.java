package com.example.oneul.infra.kafka.service;

import org.springframework.stereotype.Component;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.KafkaPublisher;
import com.example.oneul.infra.kafka.Type;

@Component
public class InsertMessageService implements MessageQueueService {
    private final KafkaPublisher kafkaPublisher;
    
    public InsertMessageService(KafkaPublisher kafkaPublisher){
        this.kafkaPublisher = kafkaPublisher;
    }
    
    @Override
    public Type getMessageType() {
        return Type.INSERT;
    }

    @Override
    public PostMessage transaction(Post post){
        PostMessage postMessage = new PostMessage(
            Type.INSERT.toString(),
            post.getId(), 
            post.getCreatedAt(), 
            post.getContent(),
            post.getWriter().getUsername());
        kafkaPublisher.sendMessage("post", postMessage);
        return postMessage;
    }
}
