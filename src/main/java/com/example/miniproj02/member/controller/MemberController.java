package com.example.miniproj02.member.controller;

import com.example.miniproj02.entity.HobbyVO;
import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;


    @RequestMapping("myPage")
    public String myPage() {
        return "member/myPage";
    }

    @RequestMapping("updateForm")
    public String updateForm(Model model) {
        List<HobbyVO> hobbyList = memberService.getHobby();
        model.addAttribute("hobbyList", hobbyList);
        return "member/updateForm";
    }

    @RequestMapping("checkMemberPwd")
    @ResponseBody
    public Map<String ,Object> checkMemberPwd(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        if(memberService.checkMemberPwd(memberVO)) {
            map.put("status", 0);
        } else {
            map.put("status", -1);
            map.put("statusMessage", "비밀번호가 틀렸습니다.");
        }
        return map;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

//        SecurityContextHolder.getContext().setAuthentication(객체);

        int updated = memberService.update(memberVO);

        if (updated == 1) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberVO, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            System.out.println("memberVO = " + memberVO);
            memberService.deleteHobby(memberVO);

            List<String> hobbies = memberVO.getHobbies();

            for (String hobby : hobbies) {
                memberVO.setHobby(hobby);
                memberService.insertHobby(memberVO);
            }

            UserDetails updatedUserDetails = memberService.loadUserByUsername(memberVO.getUsername());
            Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, authentication.getCredentials(), updatedUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

            map.put("status", 0);
            map.put("statusMessage", "수정되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "수정에 실패하였습니다.");
        }

        return map;
    }

    @RequestMapping("withdraw")
    @ResponseBody
    public Map<String, Object> withdraw(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        int updated = memberService.withdraw(memberVO);

        if (updated == 1) {
            SecurityContextHolder.clearContext();

            map.put("status", 0);
            map.put("statusMessage", "탈퇴되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "탈퇴 실패하였습니다.");
        }

        return map;
    }



}
