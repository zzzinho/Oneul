package com.example.oneul.infra.kafka.service;

import org.springframework.stereotype.Component;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.KafkaPublisher;
import com.example.oneul.infra.kafka.Type;

@Component
public class DeleteMessageService implements MessageQueueService {
    private final KafkaPublisher kafkaPublisher;

    public DeleteMessageService(KafkaPublisher kafkaPublisher){
        this.kafkaPublisher = kafkaPublisher;
    }

    @Override
    public Type getMessageType(){
        return Type.DELETE;
    }

    @Override
    public PostMessage transaction(Post post){
        PostMessage postMessage = PostMessage.builder()
                                            .id(post.getId())
                                            .build();
        kafkaPublisher.sendMessage("post", postMessage);
        return postMessage;
    }
}
