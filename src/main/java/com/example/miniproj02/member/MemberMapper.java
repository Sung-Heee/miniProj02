package com.example.miniproj02.member;

import com.example.miniproj02.entity.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberVO login(MemberVO memberVO);

    // 마지막 로그인 시간 변경
    int updateMemberLastLogin(String email);

    // 로그인시 비밀번호 틀린 횟수를 1 증가
    // 틀릭 횟수가 5회 이상이면 계정 잠김
    void loginCountInc(MemberVO memberVO);

    // 로그인 성공하면 비밀번호 틀린 횟수 초기화
    void loginCountClear(String email);

}
