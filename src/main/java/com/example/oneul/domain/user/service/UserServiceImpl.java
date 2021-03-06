package com.example.oneul.domain.user.service;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.oneul.domain.user.dao.UserRepository;
import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.exception.UserAlreadyExistException;
import com.example.oneul.domain.user.exception.WrongUsernameAndPasswordException;

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
    public UserEntity signUp(UserEntity userEntity){
        if(userRepository.findByUsername(userEntity.getUsername()).isPresent()){
            throw new UserAlreadyExistException(userEntity.getUsername() + " is already exists");
        }
        
        UserEntity user =  userRepository.save(
            UserEntity.builder().username(userEntity.getUsername())
                                .password(
                                    passwordEncoder.encode(userEntity.getPassword()))
                        .build());
        log.info(user.getPassword());
        log.info("user is created: " + user.toString());
        return user;
    }

    @Override
    public UserEntity login(UserEntity userEntity, HttpSession httpSession){
        UserEntity user =  userRepository.findByUsername(userEntity.getUsername())
                                         .orElseThrow(() -> new WrongUsernameAndPasswordException("wrong username"));

        user.matchPassword(userEntity.getPassword(), passwordEncoder);

        log.info("login user: " + userEntity.toString());
        httpSession.setAttribute("user",user);

        log.info("session id: " + httpSession.getId());
        log.info("session value: " + httpSession.getAttribute("user"));
        log.info("get session: " + httpSession.getAttribute("user").toString());
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

