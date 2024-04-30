package com.example.miniproj02.member;

import com.example.miniproj02.entity.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MemberService memberService = new MemberService();
    @RequestMapping("loginForm")
    public void getLoginView(Model model,
                             @RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
    }

    @RequestMapping("signUp")
    public String getSignUpView() {
        return "login/signUp";
    }

    @RequestMapping("insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody MemberVO memberVO, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
//        System.out.println("memberVO : " + memberVO);

        if (memberVO.getMember_email() == null || memberVO.getMember_email().length() == 0) {
            map.put("status", -1);
            map.put("statusMessage", "Null 이거나 길이가 0인 아이디는 사용할 수 없습니다.");
        } else  {
            int updated = memberService.insert(memberVO);

            if (updated == 1){
                map.put("status", 0);
                map.put("statusMessage","회원가입이 완료되었습니다.");
            } else {
                map.put("status", -1);
                map.put("statusMessage","회원가입 실패! 다시 시도해주세요.");
            }
        }

        return map;
    }
}
