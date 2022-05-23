package com.example.oneul.domain.user.dao;

import java.util.Optional;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity save(UserEntity userEntity) throws DataIntegrityViolationException;
}
