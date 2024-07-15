package com.example.miniproj02.board.mapper;

import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.page.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> getList(PageRequest pageRequest);
    int getTotalCount(PageRequest pageRequest);
    BoardVO view(BoardVO boardVO);
    int insert(BoardVO boardVO);

    BoardVO detail(BoardVO boardVO);

    int update(BoardVO boardVO);

    int delete(BoardVO boardVO);

    int incViewCount(BoardVO boardVO);

    boolean boardDelete(String boardId);

    List<BoardVO> getBoardList();
}
