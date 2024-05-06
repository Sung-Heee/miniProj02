package com.example.miniproj02.member;

import com.example.miniproj02.entity.HobbyVO;
import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.page.PageRequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    int insert(MemberVO memberVO);

    MemberVO getPwd(MemberVO memberVO);

    int update(MemberVO memberVO);

    int withdraw(MemberVO memberVO);

    MemberVO existUser(MemberVO memberVO);

    MemberVO existNickName(MemberVO memberVO);

    String getRole(String email);

    List<MemberVO> getList(PageRequestVO pageRequestVO);

    int getTotalCount(PageRequestVO pageRequestVO);

    boolean lock(String member_email);

    boolean unlock(String member_email);

    boolean delete(String member_email);

    List<HobbyVO> getHobby();

    int insertHobby(MemberVO memberVO);

    void deleteHobby(MemberVO memberVO);

    List<MemberVO> getMemberList();
}
