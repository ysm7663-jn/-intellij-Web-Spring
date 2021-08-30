package com.example.spring.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate) 도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)
// BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
// Auditing : (회계)감사

public abstract class BaseTimeEntity {
    // BaseTimeEntity 클래스는 모든 Entity의 상위 클래스가 되어, Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할

    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동 저장된다.
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime modifiedDate;

}

// Application에서 @EnableJpaAuditing 을 추가하여 JPA Auditing 활성화 시켜야 한다
// Posts 클래스에서 BaseTimeEntity를 extends(상속) 해야 한다.