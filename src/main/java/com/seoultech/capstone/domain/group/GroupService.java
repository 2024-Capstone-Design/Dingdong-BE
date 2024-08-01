package com.seoultech.capstone.domain.group;

import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;

    public GroupResponse createGroup(GroupRequest groupRequest) {

        Teacher teacher = teacherRepository.findById(groupRequest.getTeacherId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such teacher with id " + groupRequest.getTeacherId()));

        Group newGroup = groupRepository.save(
                Group.builder()
                        .name(groupRequest.getName())
                        .imageUrl(groupRequest.getImageUrl())
                        .teacher(teacher)
                        .active(true)
                        .build()
        );

        return new GroupResponse(newGroup.getId(), newGroup.getName(), newGroup.getImageUrl(), newGroup.getTeacher().toTeacherResponse());
    }
}
