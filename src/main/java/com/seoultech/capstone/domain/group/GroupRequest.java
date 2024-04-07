package com.seoultech.capstone.domain.group;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupRequest {
    private String name;
    private String imageUrl;
    private Integer organizationId;
    private Integer teacherId;
}
