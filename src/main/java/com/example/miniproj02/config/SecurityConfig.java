package com.example.miniproj02.config;

import java.net.URLEncoder;

import com.example.miniproj02.member.handler.AuthFailureHandler;
import com.example.miniproj02.member.handler.AuthSuccessHandler;
import com.example.miniproj02.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및
// 인증을 미리 체크하겠다는 설정을 활성화한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 로그인 과정에서 password를 가로챌때 해당 해쉬로 암호화해서 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //아래 부분은 의존성 주입 함수를 호출함
        auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		/*
		 csrf 토큰 활성화시 사용
		 쿠키를 생성할 때 HttpOnly 태그를 사용하면 클라이언트 스크립트가 보호된 쿠키에 액세스하는 위험을 줄일 수 있으므로 쿠키의 보안을 강화할 수 있다.
		*/

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http
                .authorizeRequests()
                .antMatchers("/", "/login/**", "/resources/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("member_email")
                .passwordParameter("member_password")
                .loginPage("/login/loginForm")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .successHandler(authSuccessHandler) // 성공시 요청을 처리할 핸들러
                .failureHandler(authFailureHandler) // 실패시 요청을 처리할 핸들러
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
                .logoutSuccessUrl("/login/loginForm")
                .invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
                .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1) // 세션 최대 허용 수 1, -1인 경우 무제한 세션 허용
                .maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
                .expiredUrl("/login/loginForm?error=true&exception=" + URLEncoder.encode("세션이 만료되었습니다. 다시 로그인 해주세요", "UTF-8"));  // 세션이 만료된 경우 이동 할 페이지를 지정
    }
}
