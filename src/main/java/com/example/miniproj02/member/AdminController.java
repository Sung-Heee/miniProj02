package com.example.miniproj02.member;

import com.example.miniproj02.board.BoardService;
import com.example.miniproj02.code.CodeService;
import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.page.PageRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    CodeService codeService;

    @RequestMapping("member")
    public String adminMain(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws ServletException, IOException {

        if (bindingResult.hasErrors()) {
            pageRequestVO = pageRequestVO.builder().build();
        }

        model.addAttribute("pageResponseVO", memberService.getList(pageRequestVO));
        model.addAttribute("sizes", codeService.getList());

        return "admin/member";
    }

    @RequestMapping("lock")
    @ResponseBody
    public Map<String, Object> lock(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        List<String> checkList = memberVO.getCheck_list();

        System.out.println("checkList = " + checkList);

        boolean success = true;

        for (String member_id : checkList) {
            boolean result = memberService.lock(member_id);
            if (!result) {
                success = false;
                break;
            }
        }

        if (success) {
            map.put("status", 0);
            map.put("statusMessage", "계정이 잠겼습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "계정 잠금에 실패하였습니다.");
        }
        return map;
    }

    @RequestMapping("unlock")
    @ResponseBody
    public Map<String, Object> unlock(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        List<String> checkList = memberVO.getCheck_list();

        System.out.println("checkList = " + checkList);

        boolean success = true;

        for (String member_id : checkList) {
            boolean result = memberService.unlock(member_id);
            if (!result) {
                success = false;
                break;
            }
        }

        if (success) {
            map.put("status", 0);
            map.put("statusMessage", "계정이 잠금이 해제 되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "계정 잠금 해제에 실패하였습니다.");
        }
        return map;
    }
}
