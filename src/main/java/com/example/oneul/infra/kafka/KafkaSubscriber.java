package com.example.oneul.infra.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.oneul.domain.post.dao.query.PostQueryRepository;
import com.example.oneul.domain.post.domain.PostDocument;

@Component
public class KafkaSubscriber {
    private final Logger log = LoggerFactory.getLogger(KafkaSubscriber.class);
    private final PostQueryRepository postQueryRepository;

    public KafkaSubscriber(PostQueryRepository postQueryRepository){
        this.postQueryRepository = postQueryRepository;
    }

    @KafkaListener(topics = "post", groupId = "post", containerFactory = "postListener")
    public void listen(PostDocument post){
        log.info("message listen: " + post.toString());
        postQueryRepository.save(post);
    }
}
