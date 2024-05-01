package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.page.PageRequestVO;
import com.example.miniproj02.page.PageResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public int insert(BoardVO boardVO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String password = bCryptPasswordEncoder.encode(boardVO.getBoard_pwd());
        boardVO.setBoard_pwd(password);

        return boardMapper.insert(boardVO);
    }

    public BoardVO detail(BoardVO boardVO) {
        BoardVO resultVO = boardMapper.detail(boardVO);
        return resultVO;
    }

    public boolean checkPwd(BoardVO boardVO) {
        BoardVO resultVO = boardMapper.detail(boardVO);
        return bCryptPasswordEncoder.matches(boardVO.getInput_pwd(), resultVO.getBoard_pwd());
    }

    public int update(BoardVO boardVO) {
        return boardMapper.update(boardVO);
    }

    public int delete(BoardVO boardVO) {
        return boardMapper.delete(boardVO);
    }
}
