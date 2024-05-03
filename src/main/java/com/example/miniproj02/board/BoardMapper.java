package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.page.PageRequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getList(PageRequestVO pageRequestVO);
    int getTotalCount(PageRequestVO pageRequestVO);
    BoardVO view(BoardVO boardVO);
    int insert(BoardVO boardVO);

    BoardVO detail(BoardVO boardVO);

    int update(BoardVO boardVO);

    int delete(BoardVO boardVO);

    int incViewCount(BoardVO boardVO);
}
