package pastry.coffeecoding.joshuablog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pastry.coffeecoding.joshuablog.dto.user.UserRequest;
import pastry.coffeecoding.joshuablog.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 인증이 되지 않은 상태에서 인증과 관련된 주소는 앞에 인티티 적지 마세요.
    // write - insert delete update(post): /리소스/식별자(PK, UK 나머지 queryString)/save or delete or update
    // read - (get) : /리소스/식별자
    @PostMapping("/join")
    public String join(UserRequest.JoinInDto joinInDto) { // x-www-form-urlencoded
        userService.회원가입(joinInDto);
        return "redirect:/loginForm"; // 302
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
}
