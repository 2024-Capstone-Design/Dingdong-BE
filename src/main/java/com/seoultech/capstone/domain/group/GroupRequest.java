package com.seoultech.capstone.domain.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupRequest {

    @Schema(description = "그룹 이름", example = "1반")
    private String name;

    @Schema(description = "그룹 이미지 URL", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "조직 ID", example = "1")
    private Integer organizationId;

    @Schema(description = "선생님 ID", example = "1")
    private Integer teacherId;
}

