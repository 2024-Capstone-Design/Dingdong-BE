package com.seoultech.capstone.domain.studentTaskProgress;

import com.seoultech.capstone.domain.studentTaskProgress.entity.StudentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentTaskRepository extends JpaRepository<StudentTask, Integer> {
    List<StudentTask> findByStudentId(Integer studentId);
    Optional<StudentTask> findByStudentIdAndTaskId(Integer studentId, Integer taskId);
}

