package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardImageFileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardImageFileMapper {
    int insert(BoardImageFileVO boardImageFileVO);

    BoardImageFileVO findById(String boardImageFileId);
}
