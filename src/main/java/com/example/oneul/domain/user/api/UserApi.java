package com.example.oneul.domain.user.api;


import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;
import com.example.oneul.domain.user.dto.SignUpDTO;
import com.example.oneul.domain.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
public class UserApi {
    private final UserService userCommandService;

    private final Logger log = LoggerFactory.getLogger(UserApi.class);

    public UserApi(UserService userCommandService){
        this.userCommandService = userCommandService;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public UserEntity signUp(@RequestBody SignUpDTO signUpDTO, HttpSession httpSession) {
        // TODO: password1, password2 같은지 검사
        UserEntity user = userCommandService.signUp(signUpDTO.toEntity(), httpSession);
        return user;
    }
}
