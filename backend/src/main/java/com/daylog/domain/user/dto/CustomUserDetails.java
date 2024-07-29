package com.daylog.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    // CustomUserInfoDto 객체를 통해 사용자 정보를 저장
    private final CustomUserInfoDto user;

    // 사용자 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 없는 사용자로 처리
        return List.of();
    }

    // 계정이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정이 만료되지 않았다고 설정
        return true;
    }

    // 계정이 잠기지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정이 잠기지 않았다고 설정
        return true;
    }

    // 자격 증명이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되지 않았다고 설정
        return true;
    }

    // 계정이 활성화 되어 있는지 확인하는 메서드
    @Override
    public boolean isEnabled() {
        // 사용자 계정이 활성화되어 있다고 설정
        return true;
    }

    // 사용자 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        // CustomUserInfoDto 객체의 비밀번호를 반환
        return user.getPassword();
    }

    // 사용자 이름(이메일)을 반환하는 메서드
    @Override
    public String getUsername() {
        // CustomUserInfoDto 객체의 이메일을 반환
        return user.getEmail();
    }
}
