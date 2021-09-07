package com.example.spring.web;

import com.example.spring.config.auth.LoginUser;
import com.example.spring.config.auth.dto.SessionUser;
import com.example.spring.service.posts.PostsService;
import com.example.spring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    // private final HttpSession httpSession; >> 중복 코드를 제거하기 위해 WebConfig, LoginUser, LoginUserArgumentResolver 를 생성함

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        // @LoginUser SessionUser user : 기존에 (User) httpSession.getAttribute("usr") 로 가져오던 세션 정보 값이 개선됨
        // 이제 어느 컨트롤러던지 @LoginUser 만 사용하면 세션 정보를 가져올 수 있게 됨
        model.addAttribute("posts", postsService.findAllDesc());

        // SessionUser user = (SessionUser) httpSession.getAttribute("user"); >> 중복 코드를 제거하기 위해 WebConfig, LoginUser, LoginUserArgumentResolver 를 생성함
        // CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 를 저장하도록 구성 >> 로그인 성공 시 httpSession.getAttribute("user") 에서 값을 가져옴
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        // 세션에 저자오딘 값이 있을 때만 model 에 userName 으로 등록, 세션에 저장된 값이 없을 경우 model 에 아무 값이 없는 상태이니 로그인 버튼이 보이게 된다
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
