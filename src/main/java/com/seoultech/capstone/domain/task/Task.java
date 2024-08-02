package com.seoultech.capstone.domain.task;

import com.seoultech.capstone.domain.fairytale.domain.Fairytale;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.question.Question;
import com.seoultech.capstone.domain.task.dto.TaskResponse;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fairytale_id", nullable = false)
    private Fairytale fairytale;

    @ManyToOne
    @JoinColumn(name = "target_classes_id", nullable = false)
    private Group targetClass;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Task(Integer id, Fairytale fairytale, Group targetClass, Teacher teacher, String title, String summary, LocalDateTime startDate, LocalDateTime finishDate, Question question, LocalDateTime createdAt) {
        this.id = id;
        this.fairytale = fairytale;
        this.targetClass = targetClass;
        this.teacher = teacher;
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.question = question;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public TaskResponse toResponse() {
        return TaskResponse.builder()
                .taskId(this.id)
                .taskTitle(this.title)
                .taskSummary(this.summary)
                .fairytaleId(this.fairytale.getId())
                .fairytaleTitle(this.fairytale.getTitle())
                .fairytaleImageUrl(this.fairytale.getImageUrl())
                .targetClassId(this.targetClass.getId())
                .targetClassName(this.targetClass.getName())
                .startDate(this.startDate)
                .finishDate(this.finishDate)
                .questionId(this.question.getId())
                .questionType(this.question.getType())
                .questionPrompt(this.question.getPrompt())
                .questionOutput(this.question.getOutput())
                .createdAt(this.createdAt)
                .build();
    }
}
