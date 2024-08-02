package com.seoultech.capstone.domain.user.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUsernameAndGroupIdAndActiveTrue(String username, Integer groupId);

    boolean existsByUsername(String username);

    List<Student> findByGroupId(Integer id);

}
