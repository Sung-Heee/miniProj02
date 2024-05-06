package com.example.miniproj02.member;

import com.example.miniproj02.entity.HobbyVO;
import com.example.miniproj02.entity.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @RequestMapping("signUp")
    public String getSignUpView(Model model) {
        List<HobbyVO> hobbyList = memberService.getHobby();
        model.addAttribute("hobbyList", hobbyList);
        return "login/signUp";
    }

    @RequestMapping("insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody MemberVO memberVO, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("memberVO : " + memberVO);

        if (memberVO.getMember_email() == null || memberVO.getMember_email().length() == 0) {
            map.put("status", -1);
            map.put("statusMessage", "Null 이거나 길이가 0인 아이디는 사용할 수 없습니다.");
        } else  {
            int updated = memberService.insert(memberVO);

            if (updated == 1){

                List<String> hobbies = memberVO.getHobbies();

                for (String hobby : hobbies) {
                    memberVO.setHobby(hobby);
                    memberService.insertHobby(memberVO);
                }

                map.put("status", 0);
                map.put("statusMessage","회원가입이 완료되었습니다.");
            } else {
                map.put("status", -1);
                map.put("statusMessage","회원가입 실패! 다시 시도해주세요.");
            }
        }

        return map;
    }

    @RequestMapping("existUser")
    @ResponseBody
    public Map<String, Object> existUser(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        MemberVO userEmail = memberService.existUser(memberVO);

        if (userEmail == null) {
            map.put("existUser", false);
            map.put("statusMessage", "사용 가능한 이메일입니다.");
        } else {
            map.put("existUser", true);
            map.put("statusMessage", "이미 존재하는 이메일입니다.");
        }


        return map;
    }

    @RequestMapping("existNickname")
    @ResponseBody
    public Map<String, Object> existNickname(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        MemberVO userNickname = memberService.existNickName(memberVO);

        if (userNickname == null) {
            map.put("existNickname", false);
            map.put("statusMessage", "사용 가능한 닉네임입니다.");
        } else {
            map.put("existNickname", true);
            map.put("statusMessage", "이미 존재하는 닉네임입니다.");
        }

        return map;
    }
}
