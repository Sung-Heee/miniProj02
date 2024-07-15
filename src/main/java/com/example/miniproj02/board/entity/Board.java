package com.example.miniproj02.board.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
    private String board_id;
    private String board_title;
    private String board_content;
    private String member_email;
    private Date board_date;
    private String view_count;
    private String board_pwd;
    private String board_writer;
}
