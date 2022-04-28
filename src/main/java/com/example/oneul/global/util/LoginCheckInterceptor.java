package com.example.oneul.global.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor{
    private String loginPage;
    private final Logger log = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    public LoginCheckInterceptor(String loginPage){
        this.loginPage = loginPage;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login check prehandler");
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null){
            log.info("no session");
            response.sendRedirect(loginPage);
            return false;
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        
        if(userEntity == null){
            log.info("session expired");
            response.sendRedirect(loginPage);
            return false;
        }
        return true;
    }
}
