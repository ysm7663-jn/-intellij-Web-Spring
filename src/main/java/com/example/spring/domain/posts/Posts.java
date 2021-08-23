package com.example.spring.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스 내의 모든 필드의 Getter 메소드를 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타냄

// @Setter가 없는 이유 : 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지 코드상으로 명확하게 구분할 수 없어, 차후 변경 시 복잡해짐
// >> Entity 클래스에서는 절대 setter 메소드를 만들지 않는다.
// Setter 없이 insert는 어떻게?? >> 기본적으론 생성자를 통해 최종값을 채운 후 DB에 삽입, 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경
// 이 강의에선 @Builder를 통해 제공되는 빌더 클래스를 사용

public class Posts {
// DB의 테이블과 매칭될 클래스 (보통 Entity 클래스라고 부름)

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK 생성 규칙을 나타냄
    // GenerationType.IDENTITY 로 해놔야지만 auto_increment가 된다
    private Long id;

    // @Column : 테이블의 칼럼을 나타냄, 굳이 작성하지 않더라도 해당 클래스의 모든 필드는 칼럼이 된다
    // 사용하는 이유 : 기본값 외에 추가로 변경한 옵션이 있을 경우
    // length : 255가 기본(확장을 위해 500으로 변경)
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    // 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    // Setter를 대신
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
