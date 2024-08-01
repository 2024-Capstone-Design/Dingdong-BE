package com.seoultech.capstone.domain.group;

import com.seoultech.capstone.domain.user.teacher.TeacherResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupResponse {

    @Schema(description = "그룹 ID", example = "1")
    private int id;

    @Schema(description = "그룹 이름", example = "유치원 A반")
    private String name;

    @Schema(description = "그룹 이미지 URL", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "선생님 정보")
    private TeacherResponse teacherResponse;

    public GroupResponse(int id, String name, String imageUrl, TeacherResponse teacherResponse) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.teacherResponse = teacherResponse;
    }
}
