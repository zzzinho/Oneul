package com.example.oneul.global.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.oneul.domain.user.domain.UserEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor{
    @Value("${login-page}")
    private String loginPage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null){
            response.sendRedirect(loginPage);
            return false;
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        
        if(userEntity == null){
            response.sendRedirect(loginPage);
            return false;
        }
        return true;
    }
}
