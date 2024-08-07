package KKSC.page.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthControllerTest {

    /*
    * 테스팅 용 매핑
    * */
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html을 반환
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // register.html을 반환
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile"; // profile.html을 반환
    }

    @GetMapping("/profile_edit")
    public String profile_edit() {
        return "profile_edit"; // profile.html을 반환
    }

    @GetMapping("/board-form")
    public String board_form() {
        return "board-form"; // profile.html을 반환
    }


}
