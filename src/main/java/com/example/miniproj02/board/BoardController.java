package com.example.miniproj02.board;

import com.example.miniproj02.code.CodeService;
import com.example.miniproj02.page.PageRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    CodeService codeService;


    // pageNO => 1 이어야하는데 만약 음수면 pageRequestVO, bindingResult 여기서 처리
    // size : 10 ~ 100 이때 만약 -10이라고 주면 pageRequestVO 이걸 가지고 bindingResult 여기서 에러 값 설정이 돼서 다시 정리해줌 (pageRequestVO = PageRequestVO.builder().build();)
    @RequestMapping("list")
    // 값에 문제가 있으면 bindingResult에 에러에 관한 걸 설정
    public String list(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws ServletException, IOException {

        if (bindingResult.hasErrors()) {
            pageRequestVO = pageRequestVO.builder().build();
        }

        model.addAttribute("pageResponseVO", boardService.getList(pageRequestVO));
        model.addAttribute("sizes", codeService.getList());

        return "board/list";
    }
}
