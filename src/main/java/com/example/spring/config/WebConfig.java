package com.example.spring.config;

import com.example.spring.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}

// LoginUserArgumentResolver 가 스프링에서 인식될 수 있도록 돕는 class

// HandlerMethodArgumentResolver : 항상 WebMvcConfigurer 의 addArgumentResolvers() 를 통해 추가해야만 함
// 다른 HandlerMethodArgumentResolver 가 필요하다면 가은 방식으로 추가해 주면 된다.
