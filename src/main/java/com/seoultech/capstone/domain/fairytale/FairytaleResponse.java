package com.seoultech.capstone.domain.fairytale;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FairytaleResponse {
    private Integer id;
    private String title;
    private String background;
    private String characters;
    private String content;
    private String teacherName;
    private LocalDateTime createdAt;

    @Builder
    public FairytaleResponse(Integer id, String title, String background, String characters, String content, String teacherName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.background = background;
        this.characters = characters;
        this.content = content;
        this.teacherName = teacherName;
        this.createdAt = createdAt;
    }
}


