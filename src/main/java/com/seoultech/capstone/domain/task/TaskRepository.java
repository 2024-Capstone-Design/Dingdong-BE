package com.seoultech.capstone.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTeacherId(Integer teacherId);

    List<Task> findByTargetClassId(Integer targetGroupId);
}

