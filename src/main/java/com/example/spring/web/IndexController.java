package com.example.spring.web;

import com.example.spring.config.auth.dto.SessionUser;
import com.example.spring.service.posts.PostsService;
import com.example.spring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 를 저장하도록 구성
        // 로그인 성공 시 httpSession.getAttribute("user") 에서 값을 가져올 수 있음

        if (user != null) {
            model.addAttribute("userName", user.getName());
            // 세션에 저장된 값이 있을 때만 model 에 userName 으로 등록
            // 세션에 저장된 값이 없을 경우, model 에 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
        }

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
