package com.example.spring.web;

import com.example.spring.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RestController : JSON으로 반환하는 컨트롤러로 만들어준다 >> ResponseBody를 통합
public class HelloController {

    @GetMapping("/hello")
    // @GetMapping : Get의 요청을 받을 수 있는 API를 만든다.
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
    // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 에노테이션
}
