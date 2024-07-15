package com.example.miniproj02.board.mapper;

import com.example.miniproj02.entity.BoardImageFileVO;
import com.example.miniproj02.entity.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardImageFileMapper {
    int insert(BoardImageFileVO boardImageFileVO);

    BoardImageFileVO findById(String boardImageFileId);

    int updateBoardNo(BoardVO boardVO);

    List<BoardImageFileVO> getBoardImageFileList(Map<String, Object> map);

    void deleteBoardToken(Map<String, Object> map);

    int deleteBoardImageFiles(Map<String, Object> map);

    List<BoardImageFileVO> getBoardImages(String board_token);
}
