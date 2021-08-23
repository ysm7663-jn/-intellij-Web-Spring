package com.example.spring.dto;

import com.example.spring.web.dto.HelloResponseDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
// JUnit 의 기본 = assertThat 사용 x
// assertJ의 assertThat 사용하는 이유
// 자동완성을 보단 확실하게 지원, 추가적으로 라이브러리가 필요없음

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        // assertThat : assertj라는 테스트 검증 라이브러리의 검증 메소드
        // 검증하고 싶은 대상을 메소드 인자로 받는다

        // isEqualTo : assertj의 동등 비교 메소드
        // assertThat == isEqualTo 의 값을 비교해서 같을 때만 성공
    }

}
