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
            if (principal instanceof UserDetails) {
                return Integer.parseInt(((UserDetails) principal).getUsername());
            } else {
                return Integer.parseInt(principal.toString());
            }
        }
        throw new CustomException(ErrorStatus.INVALID_REQUEST, "Security Context에서 인증 정보를 찾을 수 없습니다.");
    }
}
