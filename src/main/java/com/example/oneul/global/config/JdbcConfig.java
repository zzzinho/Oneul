package com.example.oneul.global.config;

import javax.sql.DataSource;

import com.example.oneul.domain.post.dao.PostCommandJdbcRespositoryImpl;
import com.example.oneul.domain.post.dao.PostCommandRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcConfig {
    private final DataSource dataSource;

    public JdbcConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public PostCommandRepository postCommandRepository(){
        return new PostCommandJdbcRespositoryImpl(dataSource);
    }
}
