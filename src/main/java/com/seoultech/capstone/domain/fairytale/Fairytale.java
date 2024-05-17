package com.seoultech.capstone.domain.fairytale;

import com.seoultech.capstone.domain.user.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "fairytales")
public class Fairytale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "background", nullable = false)
    private String background;

    @Column(name = "characters", nullable = false)
    private String characters;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Fairytale(Integer id, String title, String background, String characters, String content, Teacher teacher, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.background = background;
        this.characters = characters;
        this.content = content;
        this.teacher = teacher;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public FairytaleResponse toResponse() {
        return FairytaleResponse.builder()
                .id(this.id)
                .title(this.title)
                .background(this.background)
                .characters(this.characters)
                .content(this.content)
                .teacherName(this.teacher.getName())
                .createdAt(this.createdAt)
                .build();
    }
}
