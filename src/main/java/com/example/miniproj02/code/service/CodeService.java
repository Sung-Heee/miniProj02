package com.example.miniproj02.code.service;

import com.example.miniproj02.code.mapper.CodeMapper;
import com.example.miniproj02.entity.CodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService {

    private final CodeMapper codeMapper;

    public List<CodeVO> getList() {
        return codeMapper.getList();
    }
}
