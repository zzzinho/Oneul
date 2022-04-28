package com.example.oneul.global.error;

import com.example.oneul.domain.user.exception.UserAlreadyExistException;
import com.example.oneul.domain.user.exception.WrongUsernameAndPasswordException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected String handleException(Exception e){
        log.info(e.getMessage());
        return e.toString();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    protected String handleUserAlreadyExistException(UserAlreadyExistException e){
        log.info(e.getMessage());
        return e.toString();
    }

    @ExceptionHandler(WrongUsernameAndPasswordException.class)
    protected String handleWrongUsernameAndPasswordException(WrongUsernameAndPasswordException e){
        log.info(e.getMessage());
        return e.toString();
    }
}
