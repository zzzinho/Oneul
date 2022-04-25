package com.example.oneul.domain.user.service;

import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.exception.WrongUsernameAndPasswordException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserEntity signUp(UserEntity userEntity){
        UserEntity user = UserEntity.builder().username(userEntity.getUsername())
                                              .password(
                                                  passwordEncoder.encode(userEntity.getPassword()))
                                              .build();
        return userRepository.save(user);
    }

    @Override
    public UserEntity login(UserEntity userEntity, HttpSession httpSession){
        UserEntity user = (UserEntity) httpSession.getAttribute("user");
        if(user == null){
            user = userRepository.findByUsernameAndPassword(
                userEntity.getUsername(), 
                passwordEncoder.encode(userEntity.getPassword()))
                .orElseThrow(() -> new WrongUsernameAndPasswordException("wrong username and password"));
        }
        httpSession.setAttribute("user",user);
        log.info("session id: " + httpSession.getId());
        log.info("session value: " + httpSession.getAttribute("user"));
        return user;
    }

    @Override
    public void logout(HttpSession httpSession){
        UserEntity user = (UserEntity) httpSession.getAttribute("user");
        if(user == null) return ;
        log.info("session id: " + httpSession.getId());
        log.info("session value: " + httpSession.getAttribute("user"));
        httpSession.removeAttribute("user");
    }
}

