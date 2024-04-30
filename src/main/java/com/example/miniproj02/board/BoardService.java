package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.page.PageRequestVO;
import com.example.miniproj02.page.PageResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public PageResponseVO<BoardVO> getList(PageRequestVO pageRequestVO) {
        List<BoardVO> list = boardMapper.getList(pageRequestVO);
        int total = boardMapper.getTotalCount(pageRequestVO);

        PageResponseVO<BoardVO> pageResponseVO = PageResponseVO.<BoardVO>withAll()
                .list(list)
                .total(total)
                .size(pageRequestVO.getSize())
                .pageNo(pageRequestVO.getPageNo())
                .build();

        return pageResponseVO;
    }
}
