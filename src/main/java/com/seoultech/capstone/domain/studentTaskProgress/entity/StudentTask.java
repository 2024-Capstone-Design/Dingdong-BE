package com.seoultech.capstone.domain.studentTaskProgress.entity;

import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import com.seoultech.capstone.domain.task.Task;
import com.seoultech.capstone.domain.user.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_task")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
    private Integer studentId;

    @Column(name = "task_id", nullable = false, insertable = false, updatable = false)
    private Integer taskId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "base_img_url", nullable = true)
    private String baseImgUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "progress", nullable = false)
    private Progress progress;

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }

    public void updateCompleted(Boolean completed, LocalDateTime completionDate) {
        this.completed = completed;
        this.completionDate = completionDate;
    }
}
