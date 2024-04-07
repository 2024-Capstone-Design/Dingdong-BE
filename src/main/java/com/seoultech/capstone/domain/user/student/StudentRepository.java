package com.seoultech.capstone.domain.user.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUsernameAndActiveTrue(String username);

}
