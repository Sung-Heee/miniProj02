package com.example.miniproj02.scheduler;

import com.example.miniproj02.board.BoardImageFileMapper;
import com.example.miniproj02.board.BoardTokenMapper;
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

    @Scheduled(fixedDelay = 60000) // 60초마다 실행
    public void fileTokenAuthDelete() {
        System.out.println("첨부파일 업로드 중 완료하지 않은 파일을 삭제");

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

    //Cron 표현식을 사용하여 작업을 예약할 수 있다.
	/*
	첫 번째 * 부터
	초(0-59)
	분(0-59)
	시간(0-23)
	일(1-31)
	월(1-12)
	요일(0-6) (0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)
	Spring @Scheduled cron은 6자리 설정만 허용하며 연도 설정을 할 수 없다.

	Cron 표현식 :
		* : 모든 조건(매시, 매일, 매주처럼 사용)을 의미
		? : 설정 값 없음 (날짜와 요일에서만 사용 가능)
		- : 범위를 지정할 때
		, : 여러 값을 지정할 때
		/ : 증분값, 즉 초기값과 증가치 설정에 사용
		L : 마지막 - 지정할 수 있는 범위의 마지막 값 설정 시 사용 (날짜와 요일에서만 사용 가능)
		W : 가장 가까운 평일(weekday)을 설정할 때
	 */
}
