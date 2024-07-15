package com.example.miniproj02.member.controller;

import com.example.miniproj02.board.service.BoardService;
import com.example.miniproj02.code.service.CodeService;
import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.member.mapper.MemberMapper;
import com.example.miniproj02.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
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

    @Autowired
    MemberMapper memberMapper;

    @RequestMapping("member")
    public String adminMain(Model model) throws ServletException, IOException {
        List<MemberVO> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);

        return "admin/member";
    }

    @RequestMapping("board")
    public String list(Model model) throws ServletException, IOException {
        List<BoardVO> boardList = boardService.getBoardList();

        model.addAttribute("boardList", boardList);

        return "admin/board";
    }

    @RequestMapping("lock")
    @ResponseBody
    public Map<String, Object> lock(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        List<String> checkList = memberVO.getCheck_list();

        boolean success = true;

        for (String member_email : checkList) {
            boolean result = memberService.lock(member_email);
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

        boolean success = true;

        for (String member_email : checkList) {
            boolean result = memberService.unlock(member_email);
            memberMapper.loginCountClear(member_email);
            if (!result) {
                success = false;
                break;
            }
        }

        if (success) {
            map.put("status", 0);
            map.put("statusMessage", "계정제 잠금이 해제 되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "계정 잠금 해제에 실패하였습니다.");
        }
        return map;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody MemberVO memberVO) {
        Map<String, Object> map = new HashMap<>();

        List<String> checkList = memberVO.getCheck_list();

        boolean success = true;

        for (String member_email : checkList) {
            boolean result = memberService.delete(member_email);
            if (!result) {
                success = false;
                break;
            }
        }

        if (success) {
            map.put("status", 0);
            map.put("statusMessage", "계정이 삭제되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "계정 삭제에 실패하였습니다.");
        }
        return map;
    }

    @RequestMapping("boardDelete")
    @ResponseBody
    public Map<String, Object> boardDelete(@RequestBody BoardVO boardVO) {
        Map<String, Object> map = new HashMap<>();

        List<String> checkList = boardVO.getCheck_list();

        boolean success = true;

        for (String board_id : checkList) {
            boolean result = boardService.boardDelete(board_id);
            if (!result) {
                success = false;
                break;
            }
        }

        if (success) {
            map.put("status", 0);
            map.put("statusMessage", "게시물이 삭제되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "게시물 삭제에 실패하였습니다.");
        }
        return map;
    }
}
