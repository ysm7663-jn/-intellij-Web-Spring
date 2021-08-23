package com.example.spring;

import com.example.spring.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// JUnit에 내장된 실행자 외의 다른 실행자를 실행 >> SpringRunner
@WebMvcTest(controllers = HelloController.class)
// Web에 집중시키는 Annotation
// @Controller, ControllerAdvice 사용 가능
// @Service, @Component, @Repository 사용 불가
public class HelloControllerTest {

    @Autowired
    // @Autowired : 스프링이 관리하는 Bean을 주입 받는다
    private MockMvc mvc;
    // 웹 API를 테스트 할 때 사용
    // 스프링 MVC 테스트의 시작점


    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증 >> isOk == 200인지 확인
                .andExpect(content().string(hello)); // 응답 본문 내용을 검증
    }


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
