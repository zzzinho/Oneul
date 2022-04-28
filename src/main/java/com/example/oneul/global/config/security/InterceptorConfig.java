package com.example.oneul.global.config.security;

import com.example.oneul.global.util.LoginCheckInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${login-page}")
    private String loginPage;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        // TODO: 왜 exception이 다시 prehandler로 돌아가냐
        interceptorRegistry.addInterceptor(new LoginCheckInterceptor(loginPage))
                            .excludePathPatterns("/user/login/**", "/user/signup/**");
    }
}
