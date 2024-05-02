package com.example.miniproj02.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @RequestMapping("myPage")
    public String myPage() {
        return "member/myPage";
    }

}
