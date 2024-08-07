package com.example.miniproj02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardVO {
    private String board_id;
    private String board_title;
    private String board_content;
    private String member_email;
    private Date board_date;
    private String view_count;
    private String board_pwd;
    private String board_writer;

    private List<String> check_list;

    // 수정된 내용
    private String board_modify_content;

    // 게시물 토큰 변수 선언
    private String board_token;

    // 업로드 파일
    private MultipartFile file;

    // 첨부파일
    private BoardFileVO boardFileVO;

    // 게시물 작성할 때 입력한 비밀번호
    private String input_pwd;
}
