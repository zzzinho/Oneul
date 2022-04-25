package com.example.oneul.domain.user.command;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.repository.UserCommandRepository;

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
    public UserEntity signUp(UserEntity userEntity, HttpSession httpSession){
        // ANCHOR: 이게 맞나..?
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity user = userCommandRepository.save(new UserEntity(userEntity));

        httpSession.setAttribute("user",user);
        log.info("session id: " + httpSession.getId());
        log.info("session value: " + httpSession.getAttribute("user"));
        return user;
    }

    @Override
    public UserEntity login(UserEntity userEntity, HttpSession httpSession){
        UserEntity user = (UserEntity) httpSession.getAttribute("user");
        return user;
    }

    @Override
    public UserEntity logout(UserEntity userEntity, HttpSession httpSession){
        return new UserEntity();
    }
}

