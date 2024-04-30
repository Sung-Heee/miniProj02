package com.example.miniproj02.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication // 로그인한 사용자 정보가 있는 객체
    ) throws IOException, ServletException {

        // 로그인한 마지막 시간 수정
        memberMapper.updateMemberLastLogin(authentication.getName());

        // 로그인 실패시 카운트 초기화
        memberMapper.loginCountClear(authentication.getName());

        System.out.println("authentication : " + authentication);

        // 성공시 이동할 주소
        // 설정(config)에서 defaultSuccessUrl("/")으로 설정한 것 보다 아래의 코드로 설정한 것이 변경돼서 동작함.
        setDefaultTargetUrl("/");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
