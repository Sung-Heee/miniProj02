package com.example.miniproj02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardFileVO {
    private String board_file_id;
    private String board_id;
    private String original_filename;
    private String real_filename;
    private String content_type;
    private long size;
    private String make_date;
}
