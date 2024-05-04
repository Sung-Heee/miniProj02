package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardTokenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardTokenMapper {
    int insert(String board_token);

    int updateStatusComplete(String board_token);

    List<BoardTokenVO> listToken();

    void delete(Map<String, Object> map);
}
