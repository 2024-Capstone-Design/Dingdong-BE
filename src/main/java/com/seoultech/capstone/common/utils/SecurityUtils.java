package com.seoultech.capstone.common.utils;

import com.seoultech.capstone.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.seoultech.capstone.response.ErrorStatus.UNAUTHORIZED_INFO;

public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public static Integer getCurrentUserId() {
        String username = getCurrentUsername();
        if (username != null && username.contains("_")) {
            String[] parts = username.split("_");
            if (parts.length == 2) {
                try {
                    return Integer.valueOf(parts[1]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static boolean isCurrentUser(Integer userId) {
        Integer currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }

    public static void checkCurrentUser(Integer userId) {
        if (!isCurrentUser(userId)) {
            throw new CustomException(UNAUTHORIZED_INFO, "현재 유저 id와 일치하지 않습니다.");
        }
    }

    public static String getCurrentUserRole() {
        String username = getCurrentUsername();
        if (username != null && username.contains("_")) {
            String[] parts = username.split("_");
            if (parts.length == 2) {
                return parts[0];
            }
        }
        return null;
    }

    public static UserInfo getCurrentUserInfo() {
        String username = getCurrentUsername();
        if (username != null && username.contains("_")) {
            String[] parts = username.split("_");
            if (parts.length == 2) {
                Integer userId;
                try {
                    userId = Integer.valueOf(parts[1]);
                } catch (NumberFormatException e) {
                    userId = null;
                }
                String role = parts[0];
                return new UserInfo(userId, role);
            }
        }
        return null;
    }

    public static class UserInfo {
        private Integer userId;
        private String role;

        public UserInfo(Integer userId, String role) {
            this.userId = userId;
            this.role = role;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getRole() {
            return role;
        }
    }
}
