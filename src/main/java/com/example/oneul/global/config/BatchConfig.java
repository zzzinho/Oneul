package com.example.oneul.global.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.oneul.domain.post.dao.command.PostCommandRepository;
import com.example.oneul.domain.post.dao.query.PostQueryRepository;
import com.example.oneul.domain.post.domain.Post;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    private final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    private PostCommandRepository postCommandRepository;
    private PostQueryRepository postQueryRepository;

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
                                    
                                    postCommandRepository.saveAll(posts);
                                    // TODO: Message Queue로 전환
                                    postQueryRepository.deleteAllById(posts.stream()
                                                                            .map(post -> post.getId())
                                                                            .collect(Collectors.toList()));
                                    log.info(expiredAt + " posts(" + posts.size() + ") are deleted.");
                                    return RepeatStatus.FINISHED;
                                }).build();
    }
}
