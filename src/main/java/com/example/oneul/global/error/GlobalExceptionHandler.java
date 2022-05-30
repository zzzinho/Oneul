package com.example.oneul.global.error;

import com.example.oneul.domain.user.exception.UserAlreadyExistException;
import com.example.oneul.domain.user.exception.WrongUsernameAndPasswordException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException e){
        log.info(e.getMessage());
        return new ResponseEntity<>("user is already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongUsernameAndPasswordException.class)
    protected ResponseEntity<String> handleWrongUsernameAndPasswordException(WrongUsernameAndPasswordException e){
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
