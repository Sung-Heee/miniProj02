package com.example.miniproj02.member;

import com.example.miniproj02.entity.MemberVO;
import com.example.miniproj02.page.PageRequestVO;
import com.example.miniproj02.page.PageResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
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

    public int insert(MemberVO memberVO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String password = bCryptPasswordEncoder.encode(memberVO.getPassword());
        memberVO.setMember_pwd(password);

        return memberMapper.insert(memberVO);
    }

    public boolean checkMemberPwd(MemberVO memberVO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        MemberVO resultVO = memberMapper.getPwd(memberVO);
        return bCryptPasswordEncoder.matches(memberVO.getInput_pwd(), resultVO.getMember_pwd());
    }

    public int update(MemberVO memberVO) {
        return memberMapper.update(memberVO);
    }

    public int withdraw(MemberVO memberVO) {
        return memberMapper.withdraw(memberVO);
    }

    public MemberVO existUser(MemberVO memberVO) {
        return memberMapper.existUser(memberVO);
    }

    public MemberVO existNickName(MemberVO memberVO) {
        return memberMapper.existNickName(memberVO);
    }

    public PageResponseVO<MemberVO> getList(PageRequestVO pageRequestVO) {
        List<MemberVO> list = memberMapper.getList(pageRequestVO);
        int total = memberMapper.getTotalCount(pageRequestVO);

        PageResponseVO<MemberVO> pageResponseVO = PageResponseVO.<MemberVO>withAll()
                .list(list)
                .total(total)
                .size(pageRequestVO.getSize())
                .pageNo(pageRequestVO.getPageNo())
                .build();
        return pageResponseVO;
    }

    public boolean lock(String member_id) {
        return memberMapper.lock(member_id);
    }

    public boolean unlock(String member_id) {
        return memberMapper.unlock(member_id);
    }
}
