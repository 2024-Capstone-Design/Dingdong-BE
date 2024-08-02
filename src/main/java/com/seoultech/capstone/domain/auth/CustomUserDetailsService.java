package com.seoultech.capstone.domain.auth;

import com.seoultech.capstone.domain.auth.Enum.UserRole;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.contains("_")) {
            throw new UsernameNotFoundException("Invalid username format: " + username);
        }

        String[] parts = username.split("_");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format: " + username);
        }

        String roleStr = parts[0];
        Integer id;
        try {
            id = Integer.valueOf(parts[1]);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + parts[1]);
        }

        UserRole role = UserRole.fromPrefix(roleStr);

        switch (role) {
            case TEACHER:
                return teacherRepository.findById(id)
                        .map(teacher -> createUserDetails(username, teacher.getPassword(), "ROLE_TEACHER"))
                        .orElseThrow(() -> new UsernameNotFoundException("Teacher not found with id: " + id));
            case STUDENT:
                return studentRepository.findById(id)
                        .map(student -> createUserDetails(username, student.getPassword(), "ROLE_STUDENT"))
                        .orElseThrow(() -> new UsernameNotFoundException("Student not found with id: " + id));
            default:
                throw new UsernameNotFoundException("Invalid user role: " + role);
        }
    }

    private UserDetails createUserDetails(String username, String password, String role) {
        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}