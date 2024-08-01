package com.seoultech.capstone.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findByTeacherId(Integer teacherId);
}
