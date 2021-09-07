package com.example.spring.config.auth.dto;

import com.example.spring.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

// SessionUser : 인증된 사용자 정보만 필요 >> 나머지 정보는 불 필요하니 필드로 선언

