package com.example.spring.web.dto;

import com.example.spring.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Dto 와 Posts(Entity 클래스) 는 확실히 구분을 해야함
// Dto 클래스 : Controller 에서 결과값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번, Request와 Response용 >> view를 위한 클래스
// Entity 클래스 : 클래스와 비즈니스 로직들이 Entity 클래스를 기준으로 동작, 변경이 잦으면 안 됨

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
