package com.example.spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// @Repository   필요 없음
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // Potst 클래스로 Database를 접근하게 해줄 Interface
    // Mybatis 에선 Dao로 불림 (DB Layer)
    // JPA 에선 Repository
    // 형태 : JpaRepository<Entity 클래스, PK 타입>를 상속 >> 기본적인 CRUD 메소드가 자동 생성

    // 주의사항!
    // Entity 클래스와 기본 Entity Repository는 함께 위치해야 함 >> 도메인 패키지에서 함께 관리k

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    // SpringDataJpa 에서 제공하지 않는 메소드는 위 처럼 작성

    // 등록/수정/삭제 >> SpringDataJpa 사용 권장
    // 조회 >> Querydsl 사용 권장
}
