package com.example.oneul.domain.user.dao;

import java.util.Optional;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@RedisHash
@Repository
public interface UserCommandRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}