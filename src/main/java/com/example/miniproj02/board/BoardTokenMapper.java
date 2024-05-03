package com.example.miniproj02.board;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardTokenMapper {
    int insert(String board_token);

    int updateStatusComplete(String board_token);
}
