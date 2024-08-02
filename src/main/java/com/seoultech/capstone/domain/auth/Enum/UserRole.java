package com.seoultech.capstone.domain.auth.Enum;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum UserRole {
    STUDENT,
    TEACHER;

    public static UserRole fromPrefix(String prefix) {
        try {
            return UserRole.valueOf(prefix);
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("Invalid user role: " + prefix);
        }
    }

    public String getPrefix() {
        return this.name() + "_";
    }
}
