package com.example.oneul.repository;

import java.util.Optional;

import com.example.oneul.model.UserEntity;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@RedisHash
@Repository
public interface UserCommandRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}