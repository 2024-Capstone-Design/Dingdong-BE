package com.seoultech.capstone.domain.auth;

import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<Teacher> teacher = teacherRepository.findById(Integer.valueOf(id));
        if (teacher.isPresent()) {
            return new CustomUserDetails(teacher.get());
        }
        Optional<Student> student = studentRepository.findById(Integer.valueOf(id));
        if (student.isPresent()) {
            return new CustomUserDetails(student.get());
        }
        throw new UsernameNotFoundException("User not found with id: " + id);
    }

}
