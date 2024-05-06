package com.example.miniproj02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO implements UserDetails {
    private int member_id;
    private String member_email;
    private String member_pwd;
    private String member_name;
    private String member_nickname;
    private String member_address;
    private String member_phone;
    private String member_gender;
    private Date member_reg_date;
    private String member_update_date;
    private String member_roles;
    private String member_account_expired;
    private String member_account_locked;
    private int    member_login_count;
    private Date member_last_login_time;

    private String input_pwd;

    private List<String> check_list;

    private List<String> hobbies;
    private String hobbyList;
    private String hobby;

    private String reg_date;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<GrantedAuthority>();
        Arrays.stream(member_roles.split(",")).forEach(role -> collections.add(new SimpleGrantedAuthority("ROLE_" + role.trim())));
        return collections;
    }

    @Override
    public String getPassword() {
        return member_pwd;
    }

    @Override
    public String getUsername() {
        return member_email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return "N".equalsIgnoreCase(member_account_expired);
    }

    @Override
    public boolean isAccountNonLocked() {
        return "N".equalsIgnoreCase(member_account_locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
