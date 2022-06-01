package com.example.oneul.domain.post.service.query;

import com.example.oneul.domain.post.dao.query.PostQueryRepository;
import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostQueryServiceImpl implements PostQueryService {
    private final PostQueryRepository postQueryRepository;

    public PostQueryServiceImpl(PostQueryRepository postQueryRepository){
        this.postQueryRepository = postQueryRepository;
    }

    @Override
    public Post insertPost(Post post){
        return postQueryRepository.insert(post);
    }
    
    @Override
    public Page<Post> findAll(PageRequest pageRequest){
        return postQueryRepository.findAll(pageRequest);
    }

    @Override
    public Page<Post> findByWriter(Long writerId, PageRequest pageRequest){
        return postQueryRepository.findAll(pageRequest);
    }
}
