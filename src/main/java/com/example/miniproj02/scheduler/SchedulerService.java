package com.example.miniproj02.scheduler;

import com.example.miniproj02.board.mapper.BoardImageFileMapper;
import com.example.miniproj02.board.mapper.BoardTokenMapper;
import com.example.miniproj02.entity.BoardImageFileVO;
import com.example.miniproj02.entity.BoardTokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class SchedulerService {
    @Autowired
    BoardTokenMapper boardTokenMapper;
    @Autowired
    BoardImageFileMapper boardImageFileMapper;

    @Scheduled(fixedDelay = 60000) // 60초마다
    public void fileTokenAuthDelete() {
        // 30분 전에 생성되고 임시 상태인 token의 목록 얻기
        List<BoardTokenVO> boardTokenList = boardTokenMapper.listToken();
        if (boardTokenList.size() != 0){
            Map<String, Object> map = new HashMap<>();
            map.put("list", boardTokenList);

            // board_token을 기준으로 BoardImageFile 목록을 얻는다.
            List<BoardImageFileVO> boardImageFileList = boardImageFileMapper.getBoardImageFileList(map);
            for (BoardImageFileVO fileUpload : boardImageFileList) {
                // 저장소에 저장된 파일을 삭제한다.
                new File(fileUpload.getReal_filename()).delete();
            }

            if (boardImageFileList.size() != 0) {
                // 게시물 내용에 추가된 이미지 정보를 삭제
                boardImageFileMapper.deleteBoardToken(map);
            }

            // 임시로 사용된 게시물 토큰을 삭제한다.
            boardTokenMapper.delete(map);
        }
    }
}
