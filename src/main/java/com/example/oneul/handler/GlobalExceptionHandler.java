package com.example.oneul.handler;

import com.example.oneul.exception.UserAlreadyExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected String handleException(Exception e){
        log.debug(e.getMessage());
        return e.toString();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    protected String handleUserAlreadyExistException(UserAlreadyExistException e){
        log.debug(e.getMessage());
        return e.toString();
    }
}
