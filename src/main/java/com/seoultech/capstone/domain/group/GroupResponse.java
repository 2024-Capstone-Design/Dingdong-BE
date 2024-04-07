package com.seoultech.capstone.domain.group;

import com.seoultech.capstone.domain.user.teacher.TeacherResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupResponse {
    private int id;
    private String name;
    private String imageUrl;
    private TeacherResponse teacherResponse;

    public GroupResponse(int id, String name, String imageUrl, TeacherResponse teacherResponse) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.teacherResponse = teacherResponse;
    }
}
