package com.seoultech.capstone.domain.studentTaskProgress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTaskProgressRepository extends JpaRepository<StudentTaskProgress, Integer> {
    List<StudentTaskProgress> findByStudentId(Integer studentId);
}

