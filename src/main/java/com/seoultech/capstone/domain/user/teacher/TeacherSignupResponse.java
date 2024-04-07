package com.seoultech.capstone.domain.user.teacher;

import com.seoultech.capstone.domain.organization.Organization;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherSignupResponse {

    private int id;
    private String email;
    private String name;
    private Organization organization;

    public TeacherSignupResponse(int id, String email, String name, Organization organization) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.organization = organization;
    }
}