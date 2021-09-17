package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// oauth Test 를 위해 JpaConfig 를 별도 생성 @EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
// 항상 SpringBootApplication부터 읽기 때문에 프로젝트의 최상단에 위치해함
// 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // SpringApplication : WAS실행 (애플리케이션 실행시 내부에서 WAS를 실행하는 것을 의미 >> Tomcat 설치 필요X >> Jar 파일 실행)
        // 내장 WAS 사용하는 이유 : 언제 어디서나 같은 환경에서 스프링 부트를 배포

    }
}
