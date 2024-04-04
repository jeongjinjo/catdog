package com.example.catdog.member;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private String member_id;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return null; // 패스워드 반환
    }

    @Override
    public String getUsername() {
        return member_id; // 사용자 ID 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨 있지 않음을 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았음을 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되었음을 반환
    }
}
