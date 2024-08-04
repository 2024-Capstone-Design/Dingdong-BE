package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.organization.Organization;
import com.seoultech.capstone.domain.organization.OrganizationResponse;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeacherResponse {

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private OrganizationResponse organization;
    private String profileUrl;
    private LocalDateTime lastAccessedAt;

}

