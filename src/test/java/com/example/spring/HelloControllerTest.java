package com.example.spring;

import com.example.spring.config.auth.SecurityConfig;
import com.example.spring.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// JUnit에 내장된 실행자 외의 다른 실행자를 실행 >> SpringRunner

// security 설정 후
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
// CustomOAuth2UserService 를 스캔하지 않기 때문에 오류 발생
// @Controller, ControllerAdvice 사용 가능
// @Service, @Component, @Repository 사용 불가

// >> 스캔 대상에서 SecurityConfig 를 제거 ( excludeFilters = {@ComponentScan 부분 ... )
// 가짜 사용자를 생성



// security 설정 전
// @WebMvcTest(controllers = HelloController.class)
// Web에 집중시키는 Annotation
// JPA 기능이 작동하지 않음
// @Controller, ControllerAdvice 사용 가능
// @Service, @Component, @Repository 사용 불가
public class HelloControllerTest {

    @Autowired
    // @Autowired : 스프링이 관리하는 Bean을 주입 받는다
    private MockMvc mvc;
    // 웹 API를 테스트 할 때 사용
    // 스프링 MVC 테스트의 시작점


    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증 >> isOk == 200인지 확인
                .andExpect(content().string(hello)); // 응답 본문 내용을 검증
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws  Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        // why?? >> is 그냥 import가 되지 않음, 수기 작성시 가능
    }
}

/*
    java.lang.IllegalArgumentException: At least one JPA metamodel must be present! 오류 발생

    원인
        1. @EnableJpaAuditing 로 인해 발생 >> 해당 에노테이션을 사용하기 위해선 최소 하나의 @Entity 클래스가 필요 (@WebMvcTest 이다 보니 존재하지 않음)
        2. @EnableJapAuditing 가 @SpringBootApplication 와 함께 있다보니 @WebMvcTest 에서도 스캔하게 되어있다 >> 분리 작업 필요

    해결 : Application 에서 @EnableJpaAuditing 을 제거 >> config 패키지에 JpaConfig 를 생성
 */
