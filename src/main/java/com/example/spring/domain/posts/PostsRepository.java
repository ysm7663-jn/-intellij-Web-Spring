package com.example.spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository   필요 없음
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // Potst 클래스로 Database를 접근하게 해줄 Interface
    // Mybatis 에선 Dao로 불림 (DB Layer)
    // JPA 에선 Repository
    // 형태 : JpaRepository<Entity 클래스, PK 타입>를 상속 >> 기본적인 CRUD 메소드가 자동 생성

    // 주의사항!
    // Entity 클래스와 기본 Entity Repository는 함께 위치해야 함 >> 도메인 패키지에서 함께 관리k
}
