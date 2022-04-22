package com.example.oneul.service.command;

import com.example.oneul.exception.UserAlreadyExistException;
import com.example.oneul.model.UserEntity;
import com.example.oneul.repository.UserCommandRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final Logger log = LoggerFactory.getLogger(UserCommandServiceImpl.class);
    private final PasswordEncoder passwordEncoder;

    public UserCommandServiceImpl(UserCommandRepository userCommandRepository, PasswordEncoder passwordEncoder){
        this.userCommandRepository = userCommandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserEntity signUp(UserEntity userEntity){
        userCommandRepository.findByUsername(userEntity.getUsername()).ifPresent(user -> {
            log.debug(user.getUsername() + " is present");
            throw new UserAlreadyExistException(userEntity.getUsername() + " is already exist.");
        });
        // ANCHOR: 이게 맞나..?
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity user = new UserEntity(userEntity);
        return userCommandRepository.save(user);
    }
}

