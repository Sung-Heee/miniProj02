package com.example.miniproj02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HobbyVO {
    private String hobby_id;
    private String hobby_name;
}
