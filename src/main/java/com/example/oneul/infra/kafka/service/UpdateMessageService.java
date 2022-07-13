package com.example.oneul.infra.kafka.service;

import org.springframework.stereotype.Component;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.KafkaPublisher;
import com.example.oneul.infra.kafka.Type;

@Component
public class UpdateMessageService implements MessageQueueService {
    private final KafkaPublisher kafkaPublisher;

    public UpdateMessageService(KafkaPublisher kafkaPublisher){
        this.kafkaPublisher = kafkaPublisher;
    }

    @Override
    public Type getMessageType(){
        return Type.UPDATE;
    }

    @Override
    public PostMessage transaction(Post post) {
        PostMessage postMessage = new PostMessage(
            Type.UPDATE.toString(), 
            post.getId(), 
            post.getCreatedAt(), 
            post.getContent(), 
            post.getWriter().getUsername());
        kafkaPublisher.sendMessage("post", postMessage);
        return postMessage;
    }
}
