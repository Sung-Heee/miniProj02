package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardFileVO;
import com.example.miniproj02.entity.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardFileMapper {
    List<BoardFileVO> getList(BoardVO boardVO);
    BoardFileVO view(BoardFileVO boardFileVO);
    int delete(BoardFileVO boardFileVO);
    int insert(BoardFileVO boardFileVO);

    BoardFileVO getBoardFileVO(BoardVO boardVO);

    BoardFileVO getBoardFile(int board_file_id);

    int update(BoardFileVO boardFileVO);
}
