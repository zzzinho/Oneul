package com.example.oneul.infra.kafka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.oneul.domain.post.domain.Post;
import com.example.oneul.infra.dto.PostMessage;
import com.example.oneul.infra.kafka.Type;

@Component
public class MessageQueueFactory {
    private final Map<Type, Function<Post, PostMessage>> messageQueueServiceMap = new HashMap<>();    

    public MessageQueueFactory(List<MessageQueueService> messageQueueServices) {
        if(CollectionUtils.isEmpty(messageQueueServices)){
            throw new IllegalArgumentException("메시지 큐 구현체가 존재하지 않습니다.");
        }

        for(MessageQueueService service : messageQueueServices){
            Function<Post, PostMessage> transaction = service::transaction;
            this.messageQueueServiceMap.put(service.getMessageType(), transaction);
        }
    }

    public Function<Post, PostMessage> getTye(Type type){
        return messageQueueServiceMap.get(type);
    }
}
