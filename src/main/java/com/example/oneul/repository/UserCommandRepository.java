package com.example.oneul.repository;

import com.example.oneul.model.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommandRepository extends CrudRepository<UserEntity, Long> {
    
}
