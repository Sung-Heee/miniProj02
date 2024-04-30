package com.example.miniproj02.code;

import com.example.miniproj02.entity.CodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CodeVO> getList();
}
