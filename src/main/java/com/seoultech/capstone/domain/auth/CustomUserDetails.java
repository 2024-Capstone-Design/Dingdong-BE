package com.seoultech.capstone.domain.auth;

import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Student student) {
        this.username = student.getUsername();
        this.password = student.getPassword();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    public CustomUserDetails(Teacher teacher) {
        this.username = teacher.getEmail();
        this.password = teacher.getPassword();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_TEACHER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
}
