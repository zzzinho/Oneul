package com.example.oneul.controller;


import javax.servlet.http.HttpSession;

import com.example.oneul.DTO.SignUpDTO;
import com.example.oneul.model.UserEntity;
import com.example.oneul.service.command.UserCommandService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserCommandService userCommandService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserCommandService userCommandService){
        this.userCommandService = userCommandService;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public UserEntity signUp(@RequestBody SignUpDTO signUpDTO, HttpSession httpSession) {
        UserEntity user = userCommandService.signUp(signUpDTO.toEntity(), httpSession);
        return user;
    }
}
