package com.example.spring.config.auth;

import com.example.spring.config.auth.dto.OAuthAttributes;
import com.example.spring.config.auth.dto.SessionUser;
import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    // 구글 로그인 이후가져온 사용자의 정보들을 기반으로 가입 및 정보 수정, 세션 저장 등의 기능을 지원

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 현재 로그인 진행 중인 서비스를 구분하는 코ㄷ
        // 구글, 네이버 로그인 구분을 위해 필요
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        // userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값을 얘기함 (Primary Key 역할)
        // 구글은 기본적으로 지원(sub), but 카카오, 네이버는 지원하지 않음
        // 네이버와 구글 로그인을 동시 지원할 때 사용

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // OAuthAttributes
        // OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute를 담을 클래스
        // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 구현 >> 사용자의 이름, 프로필 사진이 변경되면 User 엔티티에도 반영
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

            return userRepository.save(user);
        }
}
