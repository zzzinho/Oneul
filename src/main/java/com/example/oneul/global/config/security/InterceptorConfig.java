package com.example.oneul.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.oneul.global.util.LoginCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${login-page}")
    private String loginPage;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(new LoginCheckInterceptor(loginPage))
                            .excludePathPatterns("/user/login/**", "/user/signup/**");
    }
}
