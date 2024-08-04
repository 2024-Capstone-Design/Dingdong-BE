package com.seoultech.capstone.domain.user.common;

import com.seoultech.capstone.common.utils.SecurityUtils;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupResponse;
import com.seoultech.capstone.domain.organization.Organization;
import com.seoultech.capstone.domain.organization.OrganizationResponse;
import com.seoultech.capstone.domain.user.common.dto.UserInfoResponse;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.student.dto.StudentResponse;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.domain.user.teacher.TeacherResponse;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public UserInfoResponse getCurrentUserInfo() {
        SecurityUtils.UserInfo currentUserInfo = SecurityUtils.getCurrentUserInfo();

        if (currentUserInfo == null) {
            throw new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "Invalid user info");
        }

        UserInfoResponse.UserInfoResponseBuilder responseBuilder = UserInfoResponse.builder()
                .role(currentUserInfo.getRole());

        switch (currentUserInfo.getRole()) {
            case "TEACHER":
                return teacherRepository.findById(currentUserInfo.getUserId())
                        .map(teacher -> responseBuilder.teacher(toTeacherResponse(teacher)).build())
                        .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND,"Teacher not found"));

            case "STUDENT":
                return studentRepository.findById(currentUserInfo.getUserId())
                        .map(student -> responseBuilder.student(toStudentResponse(student)).build())
                        .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND,"Student not found"));

            default:
                throw new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "Invalid user role");
        }
    }

    private TeacherResponse toTeacherResponse(Teacher teacher) {
        Organization organization = teacher.getOrganization();
        return TeacherResponse.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .name(teacher.getName())
                .organization(organization.toOrganizationResponse())
                .build();
    }

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .birth(student.getBirth())
                .group(toGroupResponse(student.getGroup()))
                .profileUrl(student.getProfileUrl())
                .lastAccessedAt(student.getLastAccessedAt())
                .active(student.getActive())
                .build();
    }

    private GroupResponse toGroupResponse(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getImageUrl(),
                toTeacherResponse(group.getTeacher())
        );
    }
}
