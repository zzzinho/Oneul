package com.example.oneul.global.config;

import java.time.LocalDateTime;
import java.util.List;

import com.example.oneul.domain.post.dao.PostCommandRepository;
import com.example.oneul.domain.post.domain.Post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    private final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    private PostCommandRepository postCommandRepository;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PostCommandRepository postCommandRepository){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.postCommandRepository = postCommandRepository;
    }

    @Bean 
    public Job job() {
        Job job = jobBuilderFactory.get("job")
                                   .start(step())
                                   .build();    
                                   
        return job;
    }
    
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                                .tasklet((contribution, chunkContext) -> {
                                    LocalDateTime expiredAt = LocalDateTime.now();
                                    List<Post> posts = postCommandRepository.findAllByExpiredAtLessThanAndDeletedAtIsNull(expiredAt);
                                    posts.stream().forEach(post -> {
                                        post.setDeletedAt(expiredAt);
                                    });
                                    // TODO: Query DB 한테 알려줘야함
                                    postCommandRepository.saveAll(posts);
                                    log.info(expiredAt + " posts(" + posts.size() + ") are deleted.");
                                    return RepeatStatus.FINISHED;
                                }).build();
    }
}
