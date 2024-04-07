package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.organization.Organization;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class TeacherResponse {

    private Integer id;
    private String name;
    private String phoneNumber;
    private Organization organization;
    private String profileUrl;
    private LocalDateTime lastAccessedAt;

}

