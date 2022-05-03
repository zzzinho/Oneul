package com.example.oneul.domain.post.dao;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class PostCommandJdbcRespositoryImpl implements PostCommandRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostCommandJdbcRespositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("post").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", post.getContent());
        parameters.put("writer", post.getWriter());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        post.setId(key.longValue());
        
        return new Post();
    }

    @Override
    public Post update(Post post){
        return new Post();
    }

    @Override
    public void deleteById(Long id){

    }

    @Override
    public void delete(Post post){

    }
}
