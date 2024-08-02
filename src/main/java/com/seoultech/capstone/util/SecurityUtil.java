package com.seoultech.capstone.util;

import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            if (username.contains("_")) {
                String[] parts = username.split("_");
                if (parts.length == 2) {
                    try {
                        return Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        throw new CustomException(ErrorStatus.INVALID_REQUEST, "Invalid user ID format in username.");
                    }
                }
            }

            throw new CustomException(ErrorStatus.INVALID_REQUEST, "Invalid username format.");
        }
        throw new CustomException(ErrorStatus.INVALID_REQUEST, "Security Context에서 인증 정보를 찾을 수 없습니다.");
    }
}
