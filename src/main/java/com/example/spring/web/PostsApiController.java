package com.example.spring.web;

import com.example.spring.service.posts.PostsService;
import com.example.spring.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
// @RequiredArgsConstructor : final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다.
@RestController
// JSON으로 반환하는 컨트롤러로 만들어준다 >> ResponseBody를 통합
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

}
