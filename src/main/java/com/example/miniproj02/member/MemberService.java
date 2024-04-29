package com.example.miniproj02.member;

import com.example.miniproj02.entity.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username = {}", username);

        MemberVO resultVO = memberMapper.login(MemberVO.builder().member_email(username).build());

        if (resultVO == null) {
            log.info(username + "사용자가 존재하지 않습니다.");
            throw new UsernameNotFoundException(username + "사용자가 존재하지 않습니다.");
        }

        // 로그인 횟수 카운트
        memberMapper.loginCountInc(resultVO);

        return resultVO;
    }
}
