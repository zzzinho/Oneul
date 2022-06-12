package com.example.oneul.infra.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.oneul.domain.post.domain.PostDocument;

@Component
public class KafkaPublisher {
    private final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);
    private final KafkaTemplate<String, PostDocument> kafkaTemplate;

    public KafkaPublisher(KafkaTemplate<String,PostDocument> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, PostDocument payload){
        log.info("publish message: topic: " + topic + ", payload: " + payload.toString());
        kafkaTemplate.send(topic, payload);
    }
}
